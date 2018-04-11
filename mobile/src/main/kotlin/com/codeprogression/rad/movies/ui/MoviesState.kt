package com.codeprogression.rad.movies.ui

import com.codeprogression.rad.movies.service.Movie

data class MoviesState(
        val list: List<Movie> = listOf(),
        val selected: Int = 0,
        val loading: Boolean = false,
        val error: String? = null,
        val started: Boolean = true
) {
    val isEmptyList: Boolean
        get() = list.isEmpty()
}