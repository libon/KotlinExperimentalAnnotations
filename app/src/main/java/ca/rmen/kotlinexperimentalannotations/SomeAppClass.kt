package ca.rmen.kotlinexperimentalannotations

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class SomeAppClass {
    @ExperimentalTime
    val thisIsExperimental = Duration.seconds(5)
}