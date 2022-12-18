package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test

class StrictlyPositiveIntTest {
    @Test
    fun toString_should_behave_like_an_Int(): Unit = Random
        .nextInt(1..Int.MAX_VALUE)
        .toStrictlyPositiveInt()
        .getOrThrow()
        .run { toString() assertEquals "$value" }

    @Test
    fun int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = Random.nextInt(1..Int.MAX_VALUE)
        value.toStrictlyPositiveInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toStrictlyPositiveInt_should_fail_with_a_negative_Int() {
        val result: Result<StrictlyPositiveInt> = Random
            .nextInt(Int.MIN_VALUE..0)
            .toStrictlyPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
