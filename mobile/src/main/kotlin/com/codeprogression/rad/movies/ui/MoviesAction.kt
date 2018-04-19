package com.codeprogression.rad.movies.ui

import com.codeprogression.rad.core.UserInterfaceAction
import com.codeprogression.rad.movies.service.MovieCollection

sealed class MoviesAction: UserInterfaceAction<MoviesState> {
    class StartScreen(override val page: Int = 1) : MoviesAction(), HasPagedResult {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, null, true, false, null, currentState.started)
        }
    }

    class LoadMovies(override val page: Int = 1) : MoviesAction(), HasPagedResult {
        override fun getState(currentState: MoviesState): MoviesState {
            val list = if  (page <= 1) listOf() else currentState.list
            return MoviesState(list, null, true, false, null)
        }
    }

    class RefreshMovies : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(listOf(), null, false, true, null)
        }
    }

    class LoadFailed(private val t: Throwable) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, currentState.selected, false, false, t.message)
        }
    }

    class LoadCompleted(val movies: MovieCollection) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            val movies = movies.results
            return MoviesState(movies, currentState.selected, false, false, null)
        }
    }

    class SelectMovie(val position: Int) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return MoviesState(currentState.list, position, false, false, null)
        }
    }

    interface HasPagedResult {
        val page: Int
    }
}