package kotools.types

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.number.aNegativeNumber
import kotools.types.number.shouldBe
import kotlin.jvm.JvmInline

/** Representation of negative integers, including zero. */
@JvmInline
@SinceKotools(Types, "3.2")
public value class NegativeInt
private constructor(private val value: Int) : ExplicitInt {
    internal companion object {
        infix fun of(value: Int): Result<NegativeInt> = value.takeIf { it <= 0 }
            ?.toSuccessfulResult(::NegativeInt)
            ?: Result.failure(value shouldBe aNegativeNumber)
    }

    override fun toInt(): Int = value
    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [NegativeInt], or [IllegalArgumentException] if
 * this integer is strictly positive.
 */
@SinceKotools(Types, "3.2")
public fun Int.toNegativeInt(): Result<NegativeInt> = NegativeInt of this
