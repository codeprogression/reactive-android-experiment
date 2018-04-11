package com.codeprogression.rad.movies

import com.codeprogression.rad.movies.service.DefaultMoviesService
import org.junit.Test

class MoviesServiceTest {

    @Test
    fun getList() {
        val service = DefaultMoviesService()
        service.getList(1).test()
                .await()
                .assertNoErrors()
                .assertComplete()
                .assertValue {
                    it.results.size == 20
                }
    }
}