package com.codeprogression.rad.movies

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.codeprogression.rad.R
import com.codeprogression.rad.core.BindingViewHolder
import com.codeprogression.rad.databinding.MoviesItemBinding
import com.codeprogression.rad.movies.ui.MovieItemViewState
import com.codeprogression.rad.movies.ui.MoviesAction
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MoviesAdapter(
        private val context: Context
) : RecyclerView.Adapter<BindingViewHolder<MoviesItemBinding>>() {

    val actions: PublishSubject<MoviesAction> = PublishSubject.create()
    private val list: MutableList<MovieItemViewState> = arrayListOf()

    init {
        setHasStableIds(true)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<MoviesItemBinding> {
        return BindingViewHolder.inflate(context, R.layout.movies_item, parent, false)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<MoviesItemBinding>, position: Int) {

        holder.itemView.setOnClickListener{
            actions.onNext(MoviesAction.SelectMovie(holder.adapterPosition))
        }
        holder.binding?.apply {
            state = list[position]
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].id.get().toLong()
    }

    fun update(list: List<MovieItemViewState>) {
        Single.fromCallable {
            DiffUtil.calculateDiff(Callback(this.list, list))
        }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        {
                            it.printStackTrace()
                        },
                        { result ->
                            this.list.clear()
                            this.list.addAll(list)
                            result.dispatchUpdatesTo(this)
                        }
                )
    }

    private class Callback(
            private val oldList: List<MovieItemViewState>,
            private val newList: List<MovieItemViewState>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition].id.get() == newList[newItemPosition].id.get()

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                oldList[oldItemPosition] == newList[newItemPosition]

    }
}