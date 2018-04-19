package com.codeprogression.rad.movies.ui

import android.databinding.*

class MoviesViewState : BaseObservable() {

    var list: ObservableList<MovieItemViewState> = ObservableArrayList()
        private set
    val loading: ObservableBoolean = ObservableBoolean(true)
    val refreshing: ObservableBoolean = ObservableBoolean(true)
    val selectedItem: ObservableField<String> = ObservableField()

    @get:Bindable
    val isEmpty: Boolean
        get() = list.isEmpty()

    fun fromUiState(state: MoviesState) {
        val newList = state.list.mapIndexed { index, movie ->
            MovieItemViewState(movie, index == state.selected)
        }
        if (!state.isEmptyList) {
            selectedItem.set(
                    state.selected?.let { index ->
                        state.list[index].title
                    })
        } else {
            selectedItem.set(null)
        }
        list.clear()
        list.addAll(newList)
        loading.set(state.loading)
        refreshing.set(state.refreshing)
        notifyChange()
    }
}