package kotools.types.number

import kotlinx.serialization.builtins.serializer
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Serializer

/** Representation of all integers. */
@SinceKotools(Types, "4.0")
public sealed interface AnyInt : Comparable<AnyInt> {
    /** The value to hold. */
    public val value: Int

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if it's less than the [other] value, or a positive number if it's greater
     * than the [other] value.
     */
    override fun compareTo(other: AnyInt): Int = value.compareTo(other.value)

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
