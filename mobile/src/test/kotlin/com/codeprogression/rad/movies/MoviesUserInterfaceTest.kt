package com.codeprogression.rad.movies

import com.codeprogression.rad.movies.service.MovieCollection
import com.codeprogression.rad.movies.service.MoviesService
import com.codeprogression.rad.movies.ui.MoviesUserInterface
import com.codeprogression.rad.movies.ui.MoviesAction
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MoviesUserInterfaceTest {

    @Test
    fun getResults() {
        val service: MoviesService = mock {
            on(it.getList(any())) doReturn Single.just(MovieCollection(1, 0, 0, listOf()))
        }
        val ui = MoviesUserInterface(service)

        val actions = listOf(Observable.just(MoviesAction.StartScreen()))
        val values = ui.process(actions)
                .test()
                .await()
                .assertValueCount(2)
                .values()
        assertTrue(values[0].started)
        assertTrue(values[0].list.isEmpty())
        assertTrue(values[0].isEmptyList)
        assertTrue(values[0].loading)
        assertTrue(values[1].started)
        assertTrue(values[1].list.isEmpty())
        assertTrue(values[1].isEmptyList)
        assertFalse(values[1].loading)
    }
}