package ca.rmen.kotlinexperimentalannotations

import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class MyTestApplication : MyApplication() {
    @OptIn(ExperimentalTime::class)
    override fun onCreate() {
        super.onCreate()
        TimeSource.Monotonic.markNow()
    }
}