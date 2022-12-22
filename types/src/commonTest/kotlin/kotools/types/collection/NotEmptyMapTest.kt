package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertTrue
import kotools.types.Package
import kotools.types.assertHasAMessage
import kotlin.random.Random
import kotlin.test.Test

class NotEmptyMapTest {
    @Test
    fun toString_should_behave_like_a_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        map.toNotEmptyMap()
            .getOrThrow()
            .toString() assertEquals "$map"
    }

    @Test
    fun notEmptyMapOf_should_pass() {
        val head: Pair<String, Int> = "a" to Random.nextInt()
        val tail: Array<Pair<String, Int>> = arrayOf(
            "b" to Random.nextInt(),
            "c" to Random.nextInt()
        )
        val result: NotEmptyMap<String, Int> = notEmptyMapOf(head, *tail)
        val expected: Map<String, Int> = mapOf(head, *tail)
        result shouldEqual expected
    }

    @Test
    fun map_toNotEmptyMap_should_pass_with_a_not_empty_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        val result: NotEmptyMap<String, Int> = map.toNotEmptyMap()
            .getOrThrow()
        result shouldEqual map
    }

    @Test
    fun map_toNotEmptyMap_should_fail_with_an_empty_Map() {
        val result: Result<NotEmptyMap<String, Int>> = emptyMap<String, Int>()
            .toNotEmptyMap()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }
}

class NotEmptyMapSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_have_the_qualified_name_of_NotEmptyMap_as_serial_name(): Unit =
        NotEmptyMap.serializer(String.serializer(), Int.serializer())
            .descriptor
            .serialName assertEquals "${Package.collection}.NotEmptyMap"

    @Test
    fun serialization_should_behave_like_a_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        val notEmptyMap: NotEmptyMap<String, Int> = map.toNotEmptyMap()
            .getOrThrow()
        val result: String = Json.encodeToString(notEmptyMap)
        result assertEquals Json.encodeToString(map)
    }

    @Test
    fun deserialization_should_pass_with_a_not_empty_Map() {
        val map: Map<String, Int> = mapOf("a" to Random.nextInt())
        val encoded: String = Json.encodeToString(map)
        val result: NotEmptyMap<String, Int> = Json.decodeFromString(encoded)
        result shouldEqual map
    }

    @Test
    fun deserialization_should_fail_with_an_empty_Map() {
        val map: Map<String, Int> = emptyMap()
        val encoded: String = Json.encodeToString(map)
        val exception: SerializationException = assertFailsWith {
            Json.decodeFromString<NotEmptyMap<String, Int>>(encoded)
        }
        exception.assertHasAMessage()
    }
}

private infix fun <K, V> Map<K, V>.shouldEqual(other: Map<K, V>) {
    size assertEquals other.size
    forEach {
        assertTrue { other.containsKey(it.key) }
        it.value assertEquals other[it.key]
    }
}
