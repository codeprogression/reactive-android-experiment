package com.codeprogression.rad.movies.ui

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import com.codeprogression.rad.movies.service.Movie

class MovieItemViewState() : BaseObservable() {

    constructor(movie: Movie, selected: Boolean) : this() {
        id.set(movie.id)
        title.set(movie.title)
        this.selected.set(selected)
    }

    val id: ObservableInt = ObservableInt()
    val title: ObservableField<String> = ObservableField()
    val selected: ObservableBoolean = ObservableBoolean()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MovieItemViewState) return false

        if (id.get() != other.id.get()) return false
        if (title.get() != other.title.get()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.get().hashCode()
        result = 31 * result + (title.get()?.hashCode() ?: 0)
        return result
    }

}