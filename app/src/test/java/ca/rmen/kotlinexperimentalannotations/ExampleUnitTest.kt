package ca.rmen.kotlinexperimentalannotations

import org.junit.Test

import org.junit.Assert.*
import kotlin.time.TimeSource

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val thisShouldBeOk = TimeSource.Monotonic.markNow()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}