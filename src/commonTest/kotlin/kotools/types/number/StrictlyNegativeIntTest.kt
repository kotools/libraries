package kotools.types.number

import kotools.assert.*
import kotools.types.*
import kotlin.test.Test

class StrictlyNegativeIntTest {
    // ---------- Companion ----------

    @Test
    fun min_should_be_the_minimum_of_Int(): Unit =
        StrictlyNegativeInt.min.value assertEquals Int.MIN_VALUE

    @Test
    fun max_should_be_minus1(): Unit =
        StrictlyNegativeInt.max.value assertEquals -1

    @Test
    fun random_should_return_different_values(): Unit = StrictlyNegativeInt
        .random()
        .value assertNotEquals StrictlyNegativeInt.random().value

    // ---------- Unary operations ----------

    @Test
    fun inc_should_return_the_minimum_value_with_maximum_value() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.max
        x++
        x assertEquals StrictlyNegativeInt.min
    }

    @Test
    fun inc_should_increment_the_value_by_one_with_an_initial_value_other_than_the_maximum() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        while (x.value == StrictlyNegativeInt.max.value)
            x = StrictlyNegativeInt.random()
        val initialValue: Int = x.value
        x++
        x.value assertEquals initialValue + 1
    }

    @Test
    fun dec_should_return_the_maximum_value_with_the_minimum_value() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.min
        x--
        x assertEquals StrictlyNegativeInt.max
    }

    @Test
    fun dec_should_decrement_the_value_by_one_with_an_initial_value_other_than_the_minimum() {
        var x: StrictlyNegativeInt = StrictlyNegativeInt.random()
        while (x.value == StrictlyNegativeInt.min.value)
            x = StrictlyNegativeInt.random()
        val initialValue: Int = x.value
        x--
        x.value assertEquals initialValue - 1
    }

    @Test
    fun unaryMinus_should_pass(): Unit = StrictlyNegativeInt.random()
        .pairBy { -it }
        .runMap({ first.value }) { -second.value }
        .assertEquals()

    // ---------- Conversions ----------

    @Test
    fun toString_should_pass(): Unit = StrictlyNegativeInt.random()
        .pairBy(StrictlyNegativeInt::toString)
        .runMapSecond { value.toString() }
        .assertEquals()
}

@Suppress("unused")
class StrictlyNegativeIntSerializerTest :
    IntHolderSerializerTest<StrictlyNegativeInt>(
        StrictlyNegativeIntSerializer,
        StrictlyNegativeInt.Companion::random
    )
