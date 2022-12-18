package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

internal val negativeIntRange: IntRange = Int.MIN_VALUE..-1

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
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
