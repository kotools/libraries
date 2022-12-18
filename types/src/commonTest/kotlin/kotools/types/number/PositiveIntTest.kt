package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

internal val positiveIntRange: IntRange = ZeroInt.value..Int.MAX_VALUE

class PositiveIntTest {
    @Test
    fun int_toPositiveInt_should_pass_with_a_positive_Int() {
        val value: Int = positiveIntRange.random()
        value.toPositiveInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toPositiveInt_should_fail_with_a_strictly_negative_Int() {
        val result: Result<PositiveInt> = strictlyNegativeIntRange.random()
            .toPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
