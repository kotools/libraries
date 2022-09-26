package kotools.types.int

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFalse
import kotools.assert.assertTrue
import kotlin.random.Random
import kotlin.test.Test

class IntHolderTest {
    // ---------- Binary operations ----------

    @Test
    fun plus_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x + y
        result assertEquals xValue + y
    }

    @Test
    fun plus_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x + y
        result assertEquals xValue + yValue
    }

    @Test
    fun plus_should_pass_when_adding_an_IntHolder_to_an_Int() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x + y
        result assertEquals x + yValue
    }

    @Test
    fun minus_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x - y
        result assertEquals xValue - y
    }

    @Test
    fun minus_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x - y
        result assertEquals xValue - yValue
    }

    @Test
    fun minus_should_pass_when_subtracting_an_IntHolder_from_an_Int() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x - y
        result assertEquals x - yValue
    }

    @Test
    fun times_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x * y
        result assertEquals xValue * y
    }

    @Test
    fun times_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x * y
        result assertEquals xValue * yValue
    }

    @Test
    fun times_should_pass_when_multiplying_an_Int_with_an_IntHolder() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x * y
        result assertEquals x * yValue
    }

    @Test
    fun div_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x / y
        result assertEquals xValue / y
    }

    @Test
    fun div_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x / y
        result assertEquals xValue / yValue
    }

    @Test
    fun div_should_pass_when_dividing_an_Int_by_an_IntHolder() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x / y
        result assertEquals x / yValue
    }

    // ---------- Comparisons ----------

    @Test
    fun compareTo_should_pass_with_an_Int() {
        val xValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y: Int = Random.nextInt()
        val result: Int = x compareTo y
        result assertEquals xValue.compareTo(y)
    }

    @Test
    fun compareTo_should_pass_with_an_IntHolder() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        val result: Int = x compareTo y
        result assertEquals xValue.compareTo(yValue)
    }

    @Test
    fun compareTo_should_pass_when_comparing_an_Int_with_an_IntHolder() {
        val x: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val y = IntHolder(yValue)
        val result: Int = x compareTo y
        result assertEquals x.compareTo(yValue)
    }
}

class IntHolderImplementationTest {
    @Test
    fun equals_should_pass_with_the_same_IntHolder() {
        val value: Int = Random.nextInt()
        val x = IntHolder(value)
        val y = IntHolder(value)
        assertTrue { x == y }
    }

    @Test
    fun equals_should_return_false_with_different_IntHolders() {
        val xValue: Int = Random.nextInt()
        val yValue: Int = Random.nextInt()
        val x = IntHolder(xValue)
        val y = IntHolder(yValue)
        assertFalse { x == y }
    }
}

class IntHolderSerializerTest {
    private val serializer: IntHolderSerializer<IntHolder> by lazy {
        IntHolderSerializer(builder = ::IntHolder)
    }

    @Test
    fun serialize_should_pass() {
        val value: Int = Random.nextInt()
        val x = IntHolder(value)
        val result: String = Json.encodeToString(serializer, x)
        result assertEquals Json.encodeToString(value)
    }

    @Test
    fun deserialize_should_pass() {
        val value: Int = Random.nextInt()
        val encoded: String = Json.encodeToString(value)
        val result: IntHolder = Json.decodeFromString(serializer, encoded)
        result.value assertEquals value
    }
}