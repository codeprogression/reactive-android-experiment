package com.codeprogression.rad.movies.ui

import com.codeprogression.rad.core.ScreenUserInterface
import com.codeprogression.rad.movies.service.DefaultMoviesService
import com.codeprogression.rad.movies.service.MoviesService
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MoviesUserInterface @Inject constructor(
        private val service: MoviesService = DefaultMoviesService()
) : ScreenUserInterface<MoviesAction, MoviesState>() {

    override var currentState: MoviesState = MoviesState(started = false)

    override fun getTransformers(actions: Observable<MoviesAction>): List<Observable<MoviesState>> =
            listOf(
                    actions.ofType(MoviesAction.StartScreen::class.java).compose(start()),
                    actions.ofType(MoviesAction.LoadMovies::class.java).compose(load()),
                    actions.ofType(MoviesAction.SelectMovie::class.java).compose(select())
            )

    /**
     * Handles the Start action of the screen, delegates to [load].
     * Ignored if the screen has already been started.
     */
    private fun start(): ObservableTransformer<MoviesAction, MoviesState> {
        return ObservableTransformer {
            when (currentState.started) {
                true -> it.map { action -> action.getState(currentState) }
                false -> load().apply(it)
            }
        }
    }

    /**
     * Handles the Load action.
     * Retrieves movie list from service.
     */
    private fun load(): ObservableTransformer<MoviesAction, MoviesState> {
        return ObservableTransformer {
            it.flatMap { action ->
                val page = (action as? MoviesAction.HasPagedResult)?.page ?: 1
                service.getList(page)
                        .flatMapObservable { movies ->
                            val loadCompleted = MoviesAction.LoadCompleted(movies)
                            Observable.just<MoviesAction>(loadCompleted)
                                    .startWith(action)
                        }
                        .onErrorReturn { MoviesAction.LoadFailed(it) }
                        .map { it.getState(currentState) }
            }
        }
    }

    /**
     * Handles the Select action.
     * Selects item in movie list
     */
    private fun select(): ObservableTransformer<MoviesAction.SelectMovie, MoviesState> {
        return ObservableTransformer {
            it.map { action -> action.getState(currentState) }
        }
    }
}