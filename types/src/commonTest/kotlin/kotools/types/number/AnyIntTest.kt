package kotools.types.number

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertTrue
import kotools.types.Package
import kotlin.random.Random
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

class AnyIntSerializerTest {
    private val serializer: KSerializer<AnyInt> = AnyIntSerializerImplementation

    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_AnyInt_as_serial_name(): Unit =
        serializer.descriptor.serialName assertEquals "${Package.number}.AnyInt"

    @Test
    fun serialize_should_behave_like_an_Int() {
        val x: AnyInt = strictlyPositiveIntRange.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
        val result: String = Json.encodeToString(serializer, x)
        result assertEquals Json.encodeToString(x.value)
    }

    @Test
    fun deserialize_should_pass_with_an_Int() {
        val value: Int = Random.nextInt()
        val encoded: String = Json.encodeToString(value)
        val result: AnyInt = Json.decodeFromString(serializer, encoded)
        result.value assertEquals value
    }
}
