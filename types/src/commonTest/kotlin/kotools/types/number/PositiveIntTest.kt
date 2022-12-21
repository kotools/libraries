package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.types.Package
import kotools.types.assertHasAMessage
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
            .assertHasAMessage()
    }
}

class PositiveIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_PositiveInt_as_serial_name(): Unit =
        PositiveIntSerializer.descriptor
            .serialName assertEquals "${Package.number}.PositiveInt"

    @Test
    fun serialization_should_behave_like_an_Int() {
        val x: PositiveInt = positiveIntRange.random()
            .toPositiveInt()
            .getOrThrow()
        val result: String = Json.encodeToString(PositiveIntSerializer, x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialization_should_pass_with_a_positive_Int() {
        val value: Int = positiveIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val result: PositiveInt =
            Json.decodeFromString(PositiveIntSerializer, encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_strictly_negative_Int() {
        val value: Int = strictlyNegativeIntRange.random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString(PositiveIntSerializer, encoded)
        }
        exception.assertHasAMessage()
    }
}
