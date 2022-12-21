package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test

internal val strictlyNegativeIntRange: IntRange = Int.MIN_VALUE..-1

class StrictlyNegativeIntTest {
    @Test
    fun toString_should_behave_like_an_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        value.toStrictlyNegativeInt()
            .getOrThrow()
            .toString() assertEquals "$value"
    }

    @Test
    fun int_toStrictlyNegativeInt_should_pass_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        value.toStrictlyNegativeInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toStrictlyNegativeInt_should_fail_with_a_positive_Int() {
        val result: Result<StrictlyNegativeInt> = positiveIntRange.random()
            .toStrictlyNegativeInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }
}

class StrictlyNegativeIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyNegativeInt_as_serial_name(): Unit =
        StrictlyNegativeInt.serializer()
            .descriptor
            .serialName assertEquals "${Package.number}.StrictlyNegativeInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyNegativeInt = strictlyNegativeIntRange.random()
            .toStrictlyNegativeInt()
            .getOrThrow()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyNegativeInt = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_positive_Int() {
        val value: Int = positiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyNegativeInt>(encoded)
        }
        exception.assertHasAMessage()
    }
}
