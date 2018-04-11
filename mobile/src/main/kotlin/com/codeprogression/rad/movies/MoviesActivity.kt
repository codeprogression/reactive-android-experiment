package com.codeprogression.rad.movies

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.codeprogression.rad.R
import com.codeprogression.rad.databinding.MoviesActivityBinding
import com.codeprogression.rad.movies.ui.MoviesUserInterface
import com.codeprogression.rad.movies.ui.MoviesAction
import com.codeprogression.rad.movies.ui.MoviesViewState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy


class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: MoviesActivityBinding
    private lateinit var disposables: CompositeDisposable
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var ui: MoviesUserInterface
    private val viewState = MoviesViewState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movies_activity)
        binding.state = viewState
        ui = ViewModelProviders.of(this).get(MoviesUserInterface::class.java)
        moviesAdapter = MoviesAdapter(this@MoviesActivity, ui)
        disposables = CompositeDisposable()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()

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
            moviesAdapter.actions
    )
}




