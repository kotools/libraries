package kotools.types.number

import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotlin.jvm.JvmInline

/** Representation of strictly negative integers. */
@JvmInline
@SinceKotools(Types, "1.1")
public value class StrictlyNegativeInt
private constructor(override val value: Int) : NonZeroInt, NegativeInt
