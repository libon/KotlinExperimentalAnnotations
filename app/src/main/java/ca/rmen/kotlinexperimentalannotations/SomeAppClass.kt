package ca.rmen.kotlinexperimentalannotations

import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

class SomeAppClass {
    @ExperimentalTime
    val thisIsExperimental = TimeSource.Monotonic.markNow()
}