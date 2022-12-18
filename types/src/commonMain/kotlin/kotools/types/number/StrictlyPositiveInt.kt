package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/** Representation of positive integers excluding [zero][ZeroInt]. */
@JvmInline
@SinceKotools(Types, "1.1")
public value class StrictlyPositiveInt
private constructor(override val value: Int) : NonZeroInt, PositiveInt
