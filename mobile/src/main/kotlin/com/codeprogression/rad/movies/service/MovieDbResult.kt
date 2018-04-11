package com.codeprogression.rad.movies.service

abstract class MovieDbResult<out T>(
        val page: Int,
        val totalResults: Int,
        val totalPages: Int,
        val results: List<T>
)