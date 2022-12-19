package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.types.assertHasAMessage
import kotlin.test.Test

class NonZeroIntTest {
    private companion object {
        private val ranges: Set<IntRange> =
            setOf(strictlyPositiveIntRange, strictlyNegativeIntRange)
    }

    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int = ranges.random()
            .random()
        value.toNonZeroInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = ZeroInt.value.toNonZeroInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NonZeroInt = ranges.random()
            .random()
            .toNonZeroInt()
            .getOrThrow()
        val result: String = Json.encodeToString(NonZeroIntSerializer, x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass_with_an_Int_other_than_zero() {
        val value: Int = ranges.random()
            .random()
        val encoded: String = Json.encodeToString(value)
        val result: NonZeroInt =
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_an_Int_that_equals_zero() {
        val encoded: String = Json.encodeToString(ZeroInt.value)
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(NonZeroIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
