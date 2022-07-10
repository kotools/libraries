package kotools.types.string

import kotools.types.annotations.SinceKotoolsTypes
import kotools.types.number.PositiveInt
import kotools.types.number.StrictlyPositiveInt

/**
 * Returns this value as a [NotBlankString], or throws an
 * [IllegalArgumentException] if it's blank.
 */
@SinceKotoolsTypes("1.2")
@Throws(IllegalArgumentException::class)
public fun String.toNotBlankString(): NotBlankString = NotBlankString(this)

/** Returns this value as a [NotBlankString] or `null` if it's blank. */
@SinceKotoolsTypes("1.2")
public fun String.toNotBlankStringOrNull(): NotBlankString? =
    NotBlankString orNull this

/**
 * Represents strings that can't be empty and doesn't contain only whitespace
 * characters.
 *
 * @constructor Returns the [value] as a [NotBlankString], or throws an
 * [IllegalArgumentException] if it's blank.
 */
@JvmInline
@SinceKotoolsTypes("1.2")
public value class NotBlankString(
    public val value: String
) : Comparable<NotBlankString> {
    /** Returns the first character of this [value]. */
    public val first: Char get() = value[0]

    /** Returns the length of this [value]. */
    public val length: StrictlyPositiveInt
        get() = StrictlyPositiveInt(value.length)

    init {
        require(value.isNotBlank()) { "Given value shouldn't be blank." }
    }

    /**
     * Returns the character of this [value] at the specified [index], or throws
     * an [IndexOutOfBoundsException] if the [index] is out of bounds.
     */
    @Throws(IndexOutOfBoundsException::class)
    public infix operator fun get(index: PositiveInt): Char =
        value[index.value]

    /**
     * Returns the character of this [value] at the specified [index] or `null`
     * if the [index] is out of bounds.
     */
    public infix fun getOrNull(index: PositiveInt): Char? = try {
        get(index)
    } catch (_: IndexOutOfBoundsException) {
        null
    }

    override fun compareTo(other: NotBlankString): Int =
        value.compareTo(other.value)

    override fun toString(): String = value

    public companion object {
        /**
         * Returns the [value] as a [NotBlankString] or `null` if it's blank.
         */
        public infix fun orNull(value: String): NotBlankString? = try {
            NotBlankString(value)
        } catch (_: IllegalArgumentException) {
            null
        }
    }
}
