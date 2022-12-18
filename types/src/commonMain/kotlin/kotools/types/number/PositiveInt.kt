package kotools.types.number

import kotlinx.serialization.Serializable
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.aPositiveNumber
import kotools.types.shouldBe

/** Representation of positive integers including [zero][ZeroInt]. */
@Serializable(PositiveIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface PositiveInt : AnyInt

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is [strictly negative][StrictlyNegativeInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toPositiveInt(): Result<PositiveInt> = when {
    this == ZeroInt.value -> Result.success(ZeroInt)
    this > ZeroInt.value -> toStrictlyPositiveInt()
    else -> Result.failure(this shouldBe aPositiveNumber)
}

internal object PositiveIntSerializer :
    AnyIntSerializer<PositiveInt>(Int::toPositiveInt)
