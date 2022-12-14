package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertTrue
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.test.Test
import kotlin.test.assertFalse

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

class ZeroIntSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_ZeroInt_as_serial_name(): Unit =
        ZeroInt.serializer()
            .descriptor
            .serialName assertEquals "${Package.number}.ZeroInt"

    @Test
    fun serialize_should_behave_like_the_zero_integer(): Unit = Json.run {
        encodeToString(ZeroInt) assertEquals encodeToString(ZeroInt.value)
    }

    @Test
    fun deserialize_should_pass_with_the_zero_integer() {
        val encoded: String = Json.encodeToString(ZeroInt.value)
        val result: ZeroInt = Json.decodeFromString(encoded)
        result assertEquals ZeroInt
    }

    @Test
    fun deserialize_should_fail_with_an_Int_other_than_zero() {
        val value: Int =
            setOf(strictlyPositiveIntRange, strictlyNegativeIntRange)
                .random()
                .random()
        val encoded: String = Json.encodeToString(value)
        val exception: SerializationException =
            assertFailsWith { Json.decodeFromString<ZeroInt>(encoded) }
        exception.assertHasAMessage()
    }
}
