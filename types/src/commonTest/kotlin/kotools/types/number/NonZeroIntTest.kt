package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.test.Test

class NonZeroIntTest {
    @Test
    fun int_toNonZeroInt_should_pass_with_an_Int_other_than_zero() {
        val value: Int =
            setOf(StrictlyPositiveInt.range, StrictlyNegativeInt.range)
                .random()
                .random()
        value.toNonZeroInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toNonZeroInt_should_fail_with_an_Int_that_equals_zero() {
        val result: Result<NonZeroInt> = 0.toNonZeroInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
