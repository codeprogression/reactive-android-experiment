package com.codeprogression.rad.movies

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.codeprogression.rad.R
import com.codeprogression.rad.databinding.MoviesActivityBinding
import com.codeprogression.rad.movies.ui.MoviesAction
import com.codeprogression.rad.movies.ui.MoviesUserInterface
import com.codeprogression.rad.movies.ui.MoviesViewState
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.movies_content.*


class MoviesActivity : AppCompatActivity() {
    private lateinit var disposables: CompositeDisposable
    private lateinit var binding: MoviesActivityBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var ui: MoviesUserInterface
    private lateinit var viewStateCallback: ViewStateCallback
    private lateinit var viewState: MoviesViewState
    private var moviesAdapterState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
        binding = DataBindingUtil.setContentView(this, R.layout.movies_activity)
        moviesAdapter = MoviesAdapter(this@MoviesActivity)
        setupRecyclerView()

        ui = ViewModelProviders.of(this).get(MoviesUserInterface::class.java)
        viewState = MoviesViewState()
        viewStateCallback = ViewStateCallback(this)
        binding.state = viewState
    }

    private fun restoreAdapterState() {
        moviesAdapterState?.let {
            moviesView.layoutManager.onRestoreInstanceState(it)
            moviesAdapterState = null
        }
    }

    override fun onDestroy() {
        disposables.dispose()
        binding.unbind()
        super.onDestroy()
    }


    override fun onStart() {
        super.onStart()
        viewState.addOnPropertyChangedCallback(viewStateCallback)
        ui.process(getActions())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    viewState.fromUiState(it)
                    moviesAdapter.update(viewState.list)
                }
                .addTo(disposables)
    }

    override fun onStop() {
        super.onStop()
        viewState.removeOnPropertyChangedCallback(viewStateCallback)
        disposables.clear()
    }

    override fun onPause() {
        super.onPause()
        moviesAdapterState = moviesView.layoutManager.onSaveInstanceState()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("recyclerView", moviesAdapterState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        moviesAdapterState = savedInstanceState.getParcelable("recyclerView")
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun setupRecyclerView() {
        moviesView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        moviesView.adapter = moviesAdapter
    }

    private fun getActions(): List<Observable<out MoviesAction>> = listOf(
            // screen start
            Observable.just(MoviesAction.StartScreen()),
            // swipe to refresh
            RxSwipeRefreshLayout.refreshes(moviesRefresh).map { MoviesAction.RefreshMovies() },
            // actions from recycler view items
            moviesAdapter.actions
    )

    class ViewStateCallback(private val activity: MoviesActivity) : android.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: android.databinding.Observable?, propertyId: Int) {
            synchronized(this) {
                val viewState = sender as? MoviesViewState
                viewState?.let { state ->
                    state.selectedItem.get()?.let {
                        activity.startActivity(Intent(activity, MovieDetailActivity::class.java))
                        return
                    }

                    activity.moviesRefresh.isRefreshing = state.refreshing.get()

                    activity.restoreAdapterState()
                }
            }
        }
    }
}




