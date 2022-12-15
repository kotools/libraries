package kotools.types

import kotlinx.serialization.builtins.serializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of explicit integers. */
@SinceKotools(Types, "3.2")
public sealed interface ExplicitInt {
    /** Returns this value as an [Int]. */
    public fun toInt(): Int

    /** Returns this value as a [String]. */
    override fun toString(): String
}

internal sealed class ExplicitIntSerializer<I : ExplicitInt>(
    builder: (Int) -> Result<I>
) : Serializer<I, Int>(
    delegate = Int.serializer(),
    toDelegatedType = ExplicitInt::toInt,
    toType = builder
)

/** Returns this integer's string representation as a [NotBlankString]. */
@SinceKotools(Types, "4.0")
public fun ExplicitInt.toNotBlankString(): NotBlankString = toString()
    .toNotBlankString()
    .getOrThrow()
