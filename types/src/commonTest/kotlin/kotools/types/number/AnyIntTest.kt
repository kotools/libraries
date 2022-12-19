package kotools.types.number

import kotools.assert.assertEquals
import kotools.assert.assertTrue
import kotlin.test.Test

class AnyIntTest {
    @Test
    fun compareTo_should_return_zero_with_another_AnyInt_having_the_same_value(): Unit =
        ZeroInt.compareTo(ZeroInt) assertEquals 0

    @Test
    fun compareTo_should_return_a_negative_Int_with_another_AnyInt_having_a_greater_value() {
        val x: AnyInt = strictlyNegativeIntRange.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
        val y: AnyInt = strictlyPositiveIntRange.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue { result < 0 }
    }

    @Test
    fun compareTo_should_return_a_positive_Int_with_another_AnyInt_having_a_lower_value() {
        val x: AnyInt = strictlyPositiveIntRange.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
        val y: AnyInt = strictlyNegativeIntRange.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        assertTrue { result > 0 }
    }
}
