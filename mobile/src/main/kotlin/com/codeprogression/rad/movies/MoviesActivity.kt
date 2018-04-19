package com.codeprogression.rad.movies

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
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
    private lateinit var binding: MoviesActivityBinding
    private lateinit var disposables: CompositeDisposable
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var ui: MoviesUserInterface
    private lateinit var viewStateCallback: ViewStateCallback
    private lateinit var viewState :MoviesViewState

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

    override fun onDestroy() {
        disposables.dispose()
        binding.unbind()
        super.onDestroy()
    }

    private fun setupRecyclerView() {
        binding.moviesContent?.let {
            val view = it.moviesView
            view.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            view.adapter = moviesAdapter
        }
    }

    private fun getActions(): List<Observable<out MoviesAction>> = listOf(
            Observable.just(MoviesAction.StartScreen()),
            RxSwipeRefreshLayout.refreshes(moviesRefresh).map { MoviesAction.RefreshMovies() },
            moviesAdapter.actions
    )

    class ViewStateCallback(private val activity: MoviesActivity) : android.databinding.Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: android.databinding.Observable?, propertyId: Int) {
            synchronized(this) {
                val viewState = sender as? MoviesViewState
                viewState?.let { state ->
                    state.selectedItem.get()?.let {
                        val view = activity.findViewById<View>(R.id.moviesRefresh)
                        Snackbar.make(view, it as CharSequence, Snackbar.LENGTH_SHORT).show()
                    }
                    activity.moviesRefresh.isRefreshing = state.refreshing.get()
                }
            }
        }
    }
}




