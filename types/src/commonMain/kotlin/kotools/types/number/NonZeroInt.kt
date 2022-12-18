package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.otherThanZero
import kotools.types.shouldBe

/** Representation of integers other than [zero][ZeroInt]. */
@SinceKotools(Types, "1.1")
public sealed interface NonZeroInt : AnyInt

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals [zero][ZeroInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = when {
    this > ZeroInt.value -> toStrictlyPositiveInt()
    this < ZeroInt.value -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe otherThanZero)
}
