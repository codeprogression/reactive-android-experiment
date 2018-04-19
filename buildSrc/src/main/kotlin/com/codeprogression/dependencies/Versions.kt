@file:Suppress("unused")

package com.codeprogression.dependencies

object GradlePlugin {
    private const val version = "3.1.1"
    const val plugin = "com.android.tools.build:gradle:${version}"
    const val dataBindingCompiler = "com.android.databinding:compiler:${version}"
}

object Kotlin {
    private const val version = "1.2.31"
    const val plugin: String = "org.jetbrains.kotlin:kotlin-gradle-plugin:${version}"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${version}"
    const val stdLib7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${version}"
    const val stdLib8 = "org.jetbrains.kotlin:kotlin-stdlib-jre8:${version}"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${version}"
    const val test = "org.jetbrains.kotlin:kotlin-test:${version}"
}

object Support {
    private const val version = "27.1.1"
    const val appcompatV7 = "com.android.support:appcompat-v7:${version}"
    const val design = "com.android.support:design:${version}"
    const val recyclerView = "com.android.support:recyclerview-v7:${version}"
    const val mediaRouter = "com.android.support:mediarouter-v7:${version}"
    const val constraintLayout = "com.android.support.constraint:constraint-layout:1.0.2"
    const val testRunner = "com.android.support.test:runner:1.0.1"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.1"
}

object Dagger2 {
    private const val version = "2.14.1"

    const val dagger = "com.google.dagger:dagger:${version}"
    const val compiler = "com.google.dagger:dagger-compiler:${version}"
    //If you're using classes in dagger.android you'll also want to include:

    const val android = "com.google.dagger:dagger-android:${version}"
    const val androidSupport = "com.google.dagger:dagger-android-support:${version}" // if you use the support libraries
    const val androidProcessor = "com.google.dagger:dagger-android-processor:${version}"
}

object Rx2 {
    const val android = "io.reactivex.rxjava2:rxandroid:2.0.1"
    const val kotlin = "io.reactivex.rxjava2:rxkotlin:2.1.0"
    const val relay = "com.jakewharton.rxrelay2:rxrelay:2.0.0"
    const val java = "io.reactivex.rxjava2:rxjava:2.1.9"
    const val extensions = "com.github.akarnokd:rxjava2-extensions:0.17.0"

    object Binding {
        private const val version = "2.1.1"

        object Kotlin {
            const val core = "com.jakewharton.rxbinding2:rxbinding-kotlin:${version}"
            const val design = "com.jakewharton.rxbinding2:rxbinding-design-kotlin:${version}"
            const val support = "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${version}"
            const val appcompat = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7-kotlin:${version}"
            const val recyclerView = "com.jakewharton.rxbinding2:rxbinding-recyclerview-v7-kotlin:${version}"
            const val leanback = "com.jakewharton.rxbinding2:rxbinding-leanback-v17-kotlin:${version}"
        }
    }
}

object OkHttp {
    private const val version = "3.9.0"
    const val okhttp = "com.squareup.okhttp3:okhttp:${version}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${version}"
}

object JodaTime {
    private const val version = "2.9.9"
    const val full = "joda-time:joda-time:${version}"
    const val noTzdb = "joda-time:joda-time:${version}:no-tzdb"
    const val android = "net.danlew:android.joda:${version}"
}

object Gson {
    const val gson = "com.google.code.gson:gson:2.8.2"
}

object ExoPlayer {
    private const val version = "2.7.1"
    const val core = "com.google.android.exoplayer:exoplayer:${version}"
    const val okhttp = "com.google.android.exoplayer:extension-okhttp:${version}"
}

object ArchitectureComponents {
    private const val version = "1.1.1"
    const val compiler = "android.arch.lifecycle:compiler:${version}"
    const val viewmodel = "android.arch.lifecycle:viewmodel:${version}"
    const val extensions = "android.arch.lifecycle:extensions:${version}"

}

object UnitTest {
    const val junit = "junit:junit:4.12"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin-kt1.1:1.5.0"
}
