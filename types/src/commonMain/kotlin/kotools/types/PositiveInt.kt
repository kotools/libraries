package kotools.types

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/** Representation of positive integers, including zero. */
@JvmInline
@Serializable(PositiveIntSerializer::class)
@SinceKotools(Types, "3.2")
public value class PositiveInt
private constructor(private val value: Int) : ExplicitInt,
    Comparable<PositiveInt> {
    public companion object {
        internal val range: IntRange by lazy { 0..Int.MAX_VALUE }

        internal infix fun of(value: Int): Result<PositiveInt> = value
            .takeIf { it >= 0 }
            ?.toSuccessfulResult(::PositiveInt)
            ?: Result.failure(value shouldBe aPositiveNumber)

        /** Returns a random [PositiveInt]. */
        @SinceKotools(Types, "4.0")
        public fun random(): PositiveInt = range.random()
            .toPositiveIntOrThrow()
    }

    /**
     * Compares this integer with the [other] one for order.
     * Returns zero if this integer equals the [other] one, a negative number if
     * it's less than the [other] one, or a positive number if it's greater than
     * the [other] one.
     */
    override fun compareTo(other: PositiveInt): Int =
        value.compareTo(other.value)

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

internal object PositiveIntSerializer :
    ExplicitIntSerializer<PositiveInt>(Int::toPositiveInt)

/**
 * Returns this integer as a [PositiveInt], or [IllegalArgumentException] if
 * this integer is strictly negative.
 */
@SinceKotools(Types, "3.2")
public fun Int.toPositiveInt(): Result<PositiveInt> = PositiveInt of this

@Throws(IllegalArgumentException::class)
internal fun Int.toPositiveIntOrThrow(): PositiveInt = toPositiveInt()
    .getOrThrow()
