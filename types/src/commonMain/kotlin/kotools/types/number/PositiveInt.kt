package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools

/** Representation of positive integers including [zero][ZeroInt]. */
@SinceKotools(Types, "1.1")
public sealed interface PositiveInt : AnyInt
