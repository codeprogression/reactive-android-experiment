package com.codeprogression.rad.core

import io.reactivex.Observable

interface UserInterface<in ACTION : UserInterfaceAction<out STATE>, out STATE> {
    fun process(actions: List<Observable<out ACTION>>): Observable<out STATE>
}

interface UserInterfaceAction<STATE> {
    fun getState(currentState: STATE): STATE
}