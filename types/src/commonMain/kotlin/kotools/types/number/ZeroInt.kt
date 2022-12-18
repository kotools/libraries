package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of the zero integer. */
@SinceKotools(Types, "4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override val value: Int = 0
}
