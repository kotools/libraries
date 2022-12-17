package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/** Representation of strictly positive integers, excluding zero. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class StrictlyPositiveInt
private constructor(private val value: Int) : ExplicitInt,
    Comparable<StrictlyPositiveInt> {
    public companion object {
        internal val range: IntRange by lazy { 1..Int.MAX_VALUE }

        internal infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > 0 }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)

        /** Returns a random [StrictlyPositiveInt]. */
        @SinceKotools(Types, "4.0")
        public fun random(): StrictlyPositiveInt = range.random()
            .toStrictlyPositiveInt()
            .getOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: StrictlyPositiveInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value

    /** Returns this integer as a [NonZeroInt]. */
    @SinceKotools(Types, "4.0")
    public fun toNonZeroInt(): NonZeroInt = value.toNonZeroIntOrThrow()

    /** Returns this integer as a [PositiveInt]. */
    @SinceKotools(Types, "4.0")
    public fun toPositiveInt(): PositiveInt = value.toPositiveIntOrThrow()

    override fun toString(): String = "$value"
}

internal object StrictlyPositiveIntSerializer :
    ExplicitIntSerializer<StrictlyPositiveInt>(Int::toStrictlyPositiveInt)

/**
 * Returns this integer as a [StrictlyPositiveInt], or
 * [IllegalArgumentException] if this integer is negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this
