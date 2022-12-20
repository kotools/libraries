package kotools.types.text

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Serializer
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/**
 * Representation of strings that have at least one character, excluding
 * whitespaces.
 */
@JvmInline
@Serializable(NotBlankStringSerializer::class)
@SinceKotools(Types, "4.0")
public value class NotBlankString private constructor(
    /** The string to hold. */
    public val value: String
) : Comparable<NotBlankString> {
    internal companion object {
        infix fun of(value: String): Result<NotBlankString> = value
            .takeIf(String::isNotBlank)
            ?.toSuccessfulResult(::NotBlankString)
            ?: Result.failure(
                IllegalArgumentException("Given string shouldn't be blank.")
            )
    }

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = value.length.toStrictlyPositiveInt()
            .getOrThrow()

    /**
     * Compares this [value] lexicographically with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if it's less than the [other] value, or a positive number if it's greater
     * than the [other] value.
     */
    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    /** Returns this [value]. */
    override fun toString(): String = value
}

/**
 * Returns this string as a [NotBlankString], or [IllegalArgumentException] if
 * this string is blank.
 */
@SinceKotools(Types, "4.0")
public fun String.toNotBlankString(): Result<NotBlankString> =
    NotBlankString of this

internal object NotBlankStringSerializer : Serializer<NotBlankString, String>(
    delegate = String.serializer(),
    toDelegatedType = NotBlankString::value,
    toType = String::toNotBlankString
)
