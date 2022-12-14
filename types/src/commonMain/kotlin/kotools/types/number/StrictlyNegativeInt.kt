package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of negative integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyNegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public value class StrictlyNegativeInt
private constructor(override val value: Int) : NonZeroInt, NegativeInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyNegativeInt> = value
            .takeIf { it < ZeroInt.value }
            ?.toSuccessfulResult(::StrictlyNegativeInt)
            ?: Result.failure(value shouldBe aStrictlyNegativeNumber)
    }

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyNegativeInt], or returns an
 * [IllegalArgumentException] if this integer is [positive][PositiveInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this

internal object StrictlyNegativeIntSerializer :
    AnyIntSerializer<StrictlyNegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.StrictlyNegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): StrictlyNegativeInt = value
        .toStrictlyNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aStrictlyNegativeNumber)
}
