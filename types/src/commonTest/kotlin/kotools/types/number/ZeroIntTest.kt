package kotools.types.number

import kotools.assert.assertEquals
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ZeroIntTest {
    @Test
    fun equals_should_pass_with_another_ZeroInt(): Unit =
        assertTrue { ZeroInt == ZeroInt }

    @Test
    fun equals_should_fail_with_a_value_having_a_type_other_than_ZeroInt(): Unit =
        assertFalse { ZeroInt.equals(0) }

    @Test
    fun hashCode_should_behave_like_the_zero_integer(): Unit =
        ZeroInt.hashCode() assertEquals 0.hashCode()

    @Test
    fun toString_should_behave_like_the_zero_integer(): Unit =
        "$ZeroInt" assertEquals "0"
}
