package kotools.types.experimental.number

import kotools.shared.Project
import kotools.shared.SinceKotools
import kotools.shared.StabilityLevel
import kotools.types.experimental.ExperimentalKotoolsTypesApi

/**
 * Representation of positive numbers.
 *
 * @param N The type of [Number] to hold.
 */
@ExperimentalKotoolsTypesApi
@SinceKotools(Project.Types, "3.2", StabilityLevel.Experimental)
public interface PositiveNumber<out N : Number> : ExplicitNumber<N>