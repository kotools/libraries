package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of integers other than zero. */
@SinceKotools(Types, "1.1")
public sealed interface NonZeroInt : AnyInt
