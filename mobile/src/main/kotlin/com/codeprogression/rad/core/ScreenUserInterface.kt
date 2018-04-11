package com.codeprogression.rad.core

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

abstract class ScreenUserInterface<ACTION : UserInterfaceAction<out STATE>, STATE> :
        ViewModel(), UserInterface<ACTION, STATE> {

    protected abstract var currentState: STATE

    protected abstract fun getTransformers(actions: Observable<ACTION>): List<Observable<STATE>>

    override fun process(actions: List<Observable<out ACTION>>): Observable<out STATE> {
        return Observable.merge(actions)
                .compose(transform())
                .doOnNext{
                    currentState = it
                }
                .replay(1)
                .autoConnect()
    }

    private fun transform(): ObservableTransformer<in ACTION, STATE> {
        return ObservableTransformer {
            it.publish {
                Observable.merge<STATE>(getTransformers(it))
            }
        }
    }
}
