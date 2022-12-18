package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of the zero integer. */
@SinceKotools(Types, "4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override val value: Int = 0

    /**
     * Returns `true` if the [other] value is a [ZeroInt] or `false` otherwise.
     */
    override fun equals(other: Any?): Boolean = other is ZeroInt

    /** Returns the hash code of this [value]. */
    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "$value"
}
