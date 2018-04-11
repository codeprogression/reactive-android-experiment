package com.codeprogression.rad.core

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Used to encapsulate a RecyclerView.ViewHolder tied to a ViewDataBinding class.
 *
 * @param <T> A [android.databinding.ViewDataBinding] subclass
</T> */
class BindingViewHolder<out T : ViewDataBinding>(binding: T) : RecyclerView.ViewHolder(binding.root) {

    val binding = DataBindingUtil.bind<T>(itemView)

    companion object {

        fun <T : ViewDataBinding> inflate(
                context: Context,
                @LayoutRes layoutId: Int,
                parent: ViewGroup,
                attachToRoot: Boolean
        ): BindingViewHolder<T> {
            return BindingViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(context),
                            layoutId,
                            parent,
                            attachToRoot
                    ))
        }
    }
}