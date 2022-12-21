package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.serialization.toIntSerialDescriptor
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
 * Returns this integer as a [StrictlyNegativeInt], or an
 * [IllegalArgumentException] if this integer is [positive][PositiveInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyNegativeInt(): Result<StrictlyNegativeInt> =
    StrictlyNegativeInt of this

internal object StrictlyNegativeIntSerializer :
    KSerializer<StrictlyNegativeInt> {
    override val descriptor: SerialDescriptor =
        "${Package.number}.StrictlyNegativeInt".toNotBlankString()
            .map(NotBlankString::toIntSerialDescriptor)
            .getOrThrow()

    override fun serialize(encoder: Encoder, value: StrictlyNegativeInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): StrictlyNegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyNegativeInt()
            .getOrNull()
            ?: throw SerializationException(
                value shouldBe aStrictlyNegativeNumber
            )
    }
}
