package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

internal val StrictlyNegativeInt.Companion.range: IntRange by lazy {
    Int.MIN_VALUE..-1
}

class StrictlyNegativeIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        value.toStrictlyNegativeInt()
            .getOrThrow()
            .toString() assertEquals "$value"
    }

    @Test
    fun int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = StrictlyNegativeInt.range.random()
        value.toStrictlyNegativeInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> = Random
            .nextInt(0..Int.MAX_VALUE)
            .toStrictlyNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
