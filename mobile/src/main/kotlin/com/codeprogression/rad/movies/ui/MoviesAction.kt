package com.codeprogression.rad.movies.ui

import com.codeprogression.rad.core.UserInterfaceAction
import com.codeprogression.rad.movies.service.MovieCollection

sealed class MoviesAction: UserInterfaceAction<MoviesState> {
    class StartScreen(override val page: Int = 1) : MoviesAction(), HasPagedResult {
        override fun getState(currentState: MoviesState): MoviesState {
            return currentState.copy(
                    selected = null,
                    loading = true
            )
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
            return currentState.copy(
                    loading = false,
                    refreshing = false,
                    error = t.message,
                    started = true
            )
        }
    }

    class LoadCompleted(val movies: MovieCollection) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return currentState.copy(
                    movies.results,
                    loading = false,
                    refreshing = false,
                    error = null,
                    started = true
            )
        }
    }

    class SelectMovie(private val position: Int) : MoviesAction() {
        override fun getState(currentState: MoviesState): MoviesState {
            return currentState.copy(
                    selected = position
            )
        }
    }

    interface HasPagedResult {
        val page: Int
    }
}