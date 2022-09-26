package kotools.types.int

import kotlinx.serialization.Serializable
import kotools.types.core.SinceKotoolsTypes
import kotools.types.core.tryOrNull

/**
 * Returns the [value] as a [NegativeInt], or throws an
 * [IllegalArgumentException] if the [value] is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun NegativeInt(value: Int): NegativeInt =
    NegativeIntImplementation(value)

/**
 * Returns the [value] as a [NegativeInt], or returns `null` if the [value] is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Suppress("FunctionName")
public fun NegativeIntOrNull(value: Int): NegativeInt? =
    tryOrNull { NegativeInt(value) }

/**
 * Returns this value as a [NegativeInt], or throws an
 * [IllegalArgumentException] if this value is strictly positive.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class)
public fun Int.toNegativeInt(): NegativeInt = NegativeInt(this)

/**
 * Returns this value as a [NegativeInt].
 * Throws a [NumberFormatException] if this value is not a valid representation
 * of a number, or throws an [IllegalArgumentException] if it represents a
 * strictly positive number.
 */
@SinceKotoolsTypes("3.0")
@Throws(IllegalArgumentException::class, NumberFormatException::class)
public fun String.toNegativeInt(): NegativeInt = toInt().toNegativeInt()

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is
 * strictly positive.
 */
@SinceKotoolsTypes("3.0")
public fun Int.toNegativeIntOrNull(): NegativeInt? = NegativeIntOrNull(this)

/**
 * Returns this value as a [NegativeInt], or returns `null` if this value is not
 * a valid representation of a number or if it represents a strictly positive
 * number.
 */
@SinceKotoolsTypes("3.0")
public fun String.toNegativeIntOrNull(): NegativeInt? =
    toIntOrNull()?.toNegativeIntOrNull()

/**
 * Parent of classes responsible for holding negative integers, including zero.
 */
@Serializable(NegativeIntSerializer::class)
@SinceKotoolsTypes("3.0")
public sealed interface NegativeInt : IntHolder {
    // ---------- Unary operations ----------

    /**
     * Returns this [value] incremented by one.
     * If this [value] is the [maximum][NegativeInt.max], it returns the
     * [minimum][NegativeInt.min] value instead.
     */
    public operator fun inc(): NegativeInt = if (value == max.value) min
    else NegativeInt(value + 1)

    /**
     * Returns this [value] decremented by one.
     * If this [value] is the [minimum][NegativeInt.min], it returns the
     * [maximum][NegativeInt.max] value instead.
     */
    public operator fun dec(): NegativeInt = if (value == min.value) max
    else NegativeInt(value - 1)

    /** Returns the negative of this [value]. */
    public operator fun unaryMinus(): PositiveInt = PositiveInt(-value)

    // ---------- Binary operations ----------

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: PositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyPositiveInt): NegativeInt =
        div(other.value).toNegativeInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: NegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

    /**
     * Divides this [value] by the [other] value, truncating the result to an
     * integer that is closer to `0`.
     */
    public infix operator fun div(other: StrictlyNegativeInt): PositiveInt =
        div(other.value).toPositiveInt()

    // ---------- Conversions ----------

    /**
     * Returns this [value] as a [NonZeroInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toNonZeroInt(): NonZeroInt = NonZeroInt(value)

    /**
     * Returns this [value] as a [NonZeroInt], or returns `null` if this [value]
     * equals zero.
     */
    public fun toNonZeroIntOrNull(): NonZeroInt? = NonZeroIntOrNull(value)

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or throws an
     * [IllegalArgumentException] if this [value] equals zero.
     */
    @Throws(IllegalArgumentException::class)
    public fun toStrictlyNegativeInt(): StrictlyNegativeInt =
        StrictlyNegativeInt(value)

    /**
     * Returns this [value] as a [StrictlyNegativeInt], or returns `null` if
     * this [value] equals zero.
     */
    public fun toStrictlyNegativeIntOrNull(): StrictlyNegativeInt? =
        StrictlyNegativeIntOrNull(value)

    /**
     * Returns this [value] as a [PositiveInt], or throws an
     * [IllegalArgumentException] if this [value] is strictly negative.
     */
    @Throws(IllegalArgumentException::class)
    public fun toPositiveInt(): PositiveInt = PositiveInt(value)

    /**
     * Returns this [value] as a [PositiveInt], or returns `null` if this
     * [value] is strictly negative.
     */
    public fun toPositiveIntOrNull(): PositiveInt? = PositiveIntOrNull(value)

    public companion object {
        private val range: IntRange = Int.MIN_VALUE..0

        /** The minimum value of a [NegativeInt]. */
        public val min: NegativeInt = NegativeInt(range.first)

        /** The maximum value of a [NegativeInt]. */
        public val max: NegativeInt = NegativeInt(range.last)

        /** Returns a random [NegativeInt]. */
        public val random: NegativeInt get() = range.random().toNegativeInt()
    }
}

private class NegativeIntImplementation(value: Int) : NegativeInt,
    IntHolder by IntHolder(value, { it <= 0 })

internal object NegativeIntSerializer :
    IntHolderSerializer<NegativeInt> by IntHolderSerializer(
        "NegativeInt",
        Int::toNegativeInt
    )