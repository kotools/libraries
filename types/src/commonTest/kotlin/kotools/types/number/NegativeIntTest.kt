package kotools.types.number

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.types.assertHasAMessage
import kotlin.test.Test

internal val negativeIntRange: IntRange = Int.MIN_VALUE..ZeroInt.value

class NegativeIntTest {
    @Test
    fun int_toNegativeInt_should_pass_with_a_negative_Int() {
        val value: Int = negativeIntRange.random()
        value.toNegativeInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toNegativeInt_should_fail_with_a_strictly_positive_Int() {
        val result: Result<NegativeInt> = strictlyPositiveIntRange.random()
            .toNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: NegativeInt = negativeIntRange.random()
            .toNegativeInt()
            .getOrThrow()
        val result: String = Json.encodeToString(NegativeIntSerializer, x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass_with_a_negative_Int() {
        val value: Int = negativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: NegativeInt =
            Json.decodeFromString(NegativeIntSerializer, encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_positive_Int() {
        val value: Int = strictlyPositiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: IllegalArgumentException = assertFailsWith {
            Json.decodeFromString(NegativeIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
