package kotools.types.text

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

class NotBlankStringTest {
    @Test
    fun length_should_pass() {
        val string = "hello world"
        string.toNotBlankString()
            .getOrThrow()
            .length
            .value assertEquals string.length
    }

    @Test
    fun compareTo_should_pass() {
        val x: NotBlankString = "hello".toNotBlankString()
            .getOrThrow()
        val y: NotBlankString = "world".toNotBlankString()
            .getOrThrow()
        val result: Int = x.compareTo(y)
        val expectedResult: Int = "$x".compareTo("$y")
        result assertEquals expectedResult
    }

    @Test
    fun string_toNotBlankString_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        value.toNotBlankString()
            .getOrThrow()
            .value assertEquals value
    }

    @Test
    fun string_toNotBlankString_should_fail_with_a_blank_String() {
        val result: Result<NotBlankString> = "   ".toNotBlankString()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .assertHasAMessage()
    }
}

class NotBlankStringSerializerTest {
    @ExperimentalSerializationApi
    @Test
    fun descriptor_should_be_named_with_the_qualified_name_of_NotBlankString(): Unit =
        NotBlankStringSerializer.descriptor
            .serialName assertEquals "${Package.text}.NotBlankString"

    @Test
    fun serialization_should_behave_like_a_String() {
        val value: NotBlankString = "hello world".toNotBlankString()
            .getOrThrow()
        val result: String = Json.encodeToString(value)
        val expectedResult: String = Json.encodeToString("$value")
        result assertEquals expectedResult
    }

    @Test
    fun deserialization_should_pass_with_a_not_blank_String() {
        val value = "hello world"
        val encoded: String = Json.encodeToString(value)
        val result: NotBlankString = Json.decodeFromString(encoded)
        result.value assertEquals value
    }

    @Test
    fun deserialization_should_fail_with_a_blank_String(): Unit = Json
        .encodeToString(" ")
        .let<String, SerializationException> {
            assertFailsWith { Json.decodeFromString<NotBlankString>(it) }
        }
        .assertHasAMessage()
}
