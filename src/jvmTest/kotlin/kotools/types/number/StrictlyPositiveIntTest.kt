package kotools.types.number

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.*

class StrictlyPositiveIntTest {
    // ---------- Constants & Getters ----------

    @Test
    fun `the minimum value of StrictlyPositiveInt should be 1`(): Unit =
        StrictlyPositiveInt.min.value assertEquals 1

    @Test
    fun `the maximum value of StrictlyPositiveInt should be the maximum value of PositiveInt`(): Unit =
        StrictlyPositiveInt.max.value assertEquals PositiveInt.max.value

    @Test
    fun `the random getter should pass`(): Unit =
        StrictlyPositiveInt.random.value assertNotEquals StrictlyPositiveInt.random.value

    // ---------- Builders ----------

    @Nested
    inner class Constructor {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass { StrictlyPositiveInt(value) }
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException> {
                StrictlyPositiveInt(value)
            }
        }
    }

    @Nested
    inner class OrNull {
        @Test
        fun `should pass with a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            // WHEN
            val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with a negative Int`() {
            // GIVEN
            val value: Int = NegativeInt.random.value
            // WHEN
            val result: StrictlyPositiveInt? = StrictlyPositiveInt orNull value
            // THEN
            result.assertNull()
        }
    }

    @Nested
    inner class StringToStrictlyPositiveInt {
        @Test
        fun `should pass with a string representation of a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveInt =
                assertPass(string::toStrictlyPositiveInt)
            // THEN
            result.value assertEquals value
        }

        @Test
        fun `should throw an error with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN & THEN
            assertFailsWith<NumberFormatException>(
                string::toStrictlyPositiveInt
            )
        }

        @Test
        fun `should throw an error with a string representation of a negative Int`() {
            // GIVEN
            val string = "${NegativeInt.random.value}"
            // WHEN & THEN
            assertFailsWith<IllegalArgumentException>(
                string::toStrictlyPositiveInt
            )
        }
    }

    @Nested
    inner class StringToStrictlyPositiveIntOrNull {
        @Test
        fun `should pass with a string representation of a strictly positive Int`() {
            // GIVEN
            val value: Int = StrictlyPositiveInt.random.value
            val string = "$value"
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNotNull().value assertEquals value
        }

        @Test
        fun `should return null with an invalid string`() {
            // GIVEN
            val string = ""
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }

        @Test
        fun `should return null with a string representation of a negative Int`() {
            // GIVEN
            val string = "${NegativeInt.random.value}"
            // WHEN
            val result: StrictlyPositiveInt? =
                string.toStrictlyPositiveIntOrNull()
            // THEN
            result.assertNull()
        }
    }

    // ---------- Unary operations ----------

    @Nested
    inner class Inc {
        @Test
        fun `should increment the value by 1 with a value other than the maximum value`() {
            // GIVEN
            var x: StrictlyPositiveInt = StrictlyPositiveInt.max
            while (x == StrictlyPositiveInt.max) x = StrictlyPositiveInt.random
            val oldValue: Int = x.value
            // WHEN
            x++
            // THEN
            x.value assertEquals oldValue + 1
        }

        @Test
        fun `should return the minimum value with the maximum value`() {
            // GIVEN
            var x = StrictlyPositiveInt.max
            // WHEN
            x++
            // THEN
            x assertEquals StrictlyPositiveInt.min
        }
    }

    @Nested
    inner class Dec {
        @Test
        fun `should decrement the value by 1 with a value other than the minimum value`() {
            // GIVEN
            var x: StrictlyPositiveInt = StrictlyPositiveInt.min
            while (x == StrictlyPositiveInt.min) x = StrictlyPositiveInt.random
            val oldValue: Int = x.value
            // WHEN
            x--
            // THEN
            x.value assertEquals oldValue - 1
        }

        @Test
        fun `should return the maximum value with the minimum value`() {
            // GIVEN
            var x = StrictlyPositiveInt.min
            // WHEN
            x--
            // THEN
            x assertEquals StrictlyPositiveInt.max
        }
    }

    @Test
    fun `unaryMinus() should return a StrictlyNegativeInt`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: StrictlyNegativeInt = -x
        // THEN
        result.value assertEquals -x.value
    }

    // ---------- Binary operations ----------

    @Nested
    inner class Times {
        @Test
        fun `should return a NonZeroInt with a NonZeroInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: NonZeroIntJvm = NonZeroIntJvm.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: StrictlyPositiveInt = StrictlyPositiveInt.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }

        @Test
        fun `should return a NonZeroInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NonZeroIntJvm = x * y
            // THEN
            result.value assertEquals x.value * y.value
        }
    }

    @Nested
    inner class Div {
        // ---------- PositiveInt ----------

        @Test
        fun `should return a PositiveInt with a PositiveInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: PositiveInt = PositiveInt.random
            // WHEN
            val result: PositiveInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a PositiveInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y = PositiveInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyPositiveInt ----------

        @Test
        fun `should return a PositiveInt with a StrictlyPositiveInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: StrictlyPositiveInt = StrictlyPositiveInt.random
            // WHEN
            val result: PositiveInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }

        // ---------- NegativeInt ----------

        @Test
        fun `should return a NegativeInt with a NegativeInt other than 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: NegativeInt = NegativeInt.random
            // WHEN
            val result: NegativeInt = assertPass { x / y }
            // THEN
            result.value assertEquals x.value / y.value
        }

        @Test
        fun `should throw an error with a NegativeInt that equals 0`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y = NegativeInt(0)
            // WHEN & THEN
            assertFailsWith<ArithmeticException> { x / y }
        }

        // ---------- StrictlyNegativeInt ----------

        @Test
        fun `should return a NegativeInt with a StrictlyNegativeInt`() {
            // GIVEN
            val x: StrictlyPositiveInt = StrictlyPositiveInt.random
            val y: StrictlyNegativeInt = StrictlyNegativeInt.random
            // WHEN
            val result: NegativeInt = x / y
            // THEN
            result.value assertEquals x.value / y.value
        }
    }

    // ---------- Conversions ----------

    @Test
    fun `toNonZeroInt() should pass`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: NonZeroIntJvm = x.toNonZeroInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toPositiveInt() should pass`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: PositiveInt = x.toPositiveInt()
        // THEN
        result.value assertEquals x.value
    }

    @Test
    fun `toString() should pass`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: String = x.toString()
        // THEN
        result assertEquals "${x.value}"
    }
}

class StrictlyPositiveIntSerializerTest {
    @Test
    fun `should serialize properly the class`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        // WHEN
        val result: String = Json.encodeToString(x)
        // THEN
        result assertEquals "$x"
    }

    @Test
    fun `should deserialize properly the class`() {
        // GIVEN
        val x: StrictlyPositiveInt = StrictlyPositiveInt.random
        val encodedString: String = Json.encodeToString(x)
        // WHEN
        val result: StrictlyPositiveInt = Json.decodeFromString(encodedString)
        // THEN
        result.value assertEquals x.value
    }
}