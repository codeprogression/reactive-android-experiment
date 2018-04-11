package com.codeprogression.rad.movies.ui

import android.databinding.*

class MoviesViewState : BaseObservable() {

    var list: ObservableList<MovieItemViewState> = ObservableArrayList()
        private set
    val selected: ObservableInt = ObservableInt()

    @get:Bindable
    val isEmpty: Boolean
        get() = list.isEmpty()

    fun fromUiState(state: MoviesState) {
        val newList = state.list.mapIndexed { index, movie ->
            MovieItemViewState(movie, index == state.selected)
        }
        selected.set(state.selected)
        list.clear()
        list.addAll(newList)
        notifyChange()
    }

}