package com.codeprogression.rad.movies.ui

import com.codeprogression.rad.movies.service.MovieCollection
import com.codeprogression.rad.core.UserInterfaceAction

sealed class MoviesAction: UserInterfaceAction<MoviesState> {
    class StartScreen(override val page: Int = 1) : MoviesAction(), HasPagedResult {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, currentState.selected, true, null)
        }
    }

    class LoadMovies(override val page: Int = 1) : MoviesAction(), HasPagedResult {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, currentState.selected, true, null)
        }
    }

    class LoadFailed(private val t: Throwable) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, currentState.selected, false, t.message)
        }
    }

    class LoadCompleted(val movies: MovieCollection) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            val movies = movies.results
            return MoviesState(movies, currentState.selected, false, null)
        }
    }

    class SelectMovie(val position: Int) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, position, false, null)
        }

    }

    interface HasPagedResult {
        val page: Int
    }
}