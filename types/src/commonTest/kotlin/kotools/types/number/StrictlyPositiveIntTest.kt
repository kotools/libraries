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

internal val strictlyPositiveIntRange: IntRange = 1..Int.MAX_VALUE

class StrictlyPositiveIntTest {
    @Test
    fun toString_should_behave_like_an_Int(): Unit = strictlyPositiveIntRange
        .random()
        .toStrictlyPositiveInt()
        .getOrThrow()
        .run { toString() assertEquals "$value" }

    @Test
    fun int_toStrictlyPositiveInt_should_pass_with_a_strictly_positive_Int() {
        val value: Int = strictlyPositiveIntRange.random()
        value.toStrictlyPositiveInt()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun int_toStrictlyPositiveInt_should_fail_with_a_negative_Int() {
        val result: Result<StrictlyPositiveInt> = negativeIntRange.random()
            .toStrictlyPositiveInt()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }
}

class StrictlyPositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_StrictlyPositiveInt_as_serial_name(): Unit =
        StrictlyPositiveInt.serializer()
            .descriptor
            .serialName assertEquals "${Package.number}.StrictlyPositiveInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: StrictlyPositiveInt = strictlyPositiveIntRange.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
        val result: String = Json.encodeToString(x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass_with_a_strictly_positive_Int() {
        val value: Int = strictlyPositiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: StrictlyPositiveInt = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_negative_Int() {
        val value: Int = negativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<StrictlyPositiveInt>(encoded)
        }
        exception.assertHasAMessage()
    }
}
