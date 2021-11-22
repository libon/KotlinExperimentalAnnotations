package ca.rmen.kotlinexperimentalannotations

import android.app.Application
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

open class MyApplication : Application() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate() {
        super.onCreate()
        TimeSource.Monotonic.markNow()
    }
}