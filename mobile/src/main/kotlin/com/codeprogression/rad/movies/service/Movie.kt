package com.codeprogression.rad.movies.service

data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val adult: Boolean,
        val releaseDate: String,
        val popularity: Double,
        val voteCount: Int,
        val voteAverage: Double,
        val posterPath: String?,
        val backdropPath: String?,
        val genreIds: List<Int>,
        val originalTitle: String,
        val originalLanguage: String,
        val video: Boolean
)

class MovieCollection(
        page: Int,
        totalResults: Int,
        totalPages: Int,
        results: List<Movie>
) : MovieDbResult<Movie>(page, totalResults, totalPages, results)