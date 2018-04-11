package com.codeprogression.rad

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class MobileApplication:Application(){
    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
    }
}