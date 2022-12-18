package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.aStrictlyPositiveNumber
import kotools.types.shouldBe
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of positive integers excluding [zero][ZeroInt]. */
@JvmInline
@SinceKotools(Types, "1.1")
public value class StrictlyPositiveInt
private constructor(override val value: Int) : NonZeroInt, PositiveInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > 0 }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)
    }

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is [negative][NegativeInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this
