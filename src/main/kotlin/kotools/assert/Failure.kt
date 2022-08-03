package kotools.assert

import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

/**
 * Asserts that the [block] function doesn't throw an error and returns its
 * result.
 */
@SinceKotoolsAssert("2.1")
public fun <T> assertPass(block: () -> T): T = assertDoesNotThrow(block)

/** Asserts that the [block] function throws an error and returns this error. */
@SinceKotoolsAssert("2.1")
public inline fun assertFails(block: () -> Unit): Throwable = assertFails(block)

/**
 * Asserts that the [block] function throws an error of type [T], and returns
 * this error.
 */
@SinceKotoolsAssert("2.1")
public inline fun <reified T : Throwable> assertFailsWith(
    block: () -> Unit
): T = assertFailsWith(block = block)
