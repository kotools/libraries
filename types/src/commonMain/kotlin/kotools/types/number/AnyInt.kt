package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of all integers. */
@SinceKotools(Types, "4.0")
public sealed interface AnyInt {
    /** The value to hold. */
    public val value: Int

    /** Returns this [value] as a [String]. */
    override fun toString(): String
}
