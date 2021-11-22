package ca.rmen.kotlinexperimentalannotations

import kotlin.time.TimeSource

class SomeAppClass {
    val thisIsExperimental = TimeSource.Monotonic.markNow()
}