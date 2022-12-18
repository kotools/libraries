package kotools.types.number

import kotlinx.serialization.builtins.serializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Serializer

/** Representation of all integers. */
@SinceKotools(Types, "4.0")
public sealed interface AnyInt {
    /** The value to hold. */
    public val value: Int

    /** Returns this [value] as a [String]. */
    override fun toString(): String
}

internal sealed class AnyIntSerializer<I : AnyInt>(
    builder: (Int) -> Result<I>
) : Serializer<I, Int>(
    delegate = Int.serializer(),
    toDelegatedType = { it.value },
    toType = builder
)
