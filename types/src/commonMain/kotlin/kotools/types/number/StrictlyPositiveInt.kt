package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.toSuccessfulResult
import kotlin.jvm.JvmInline

/** Representation of positive integers excluding [zero][ZeroInt]. */
@JvmInline
@Serializable(StrictlyPositiveIntSerializer::class)
@SinceKotools(Types, "1.1")
public value class StrictlyPositiveInt
private constructor(override val value: Int) : NonZeroInt, PositiveInt {
    internal companion object {
        infix fun of(value: Int): Result<StrictlyPositiveInt> = value
            .takeIf { it > ZeroInt.value }
            ?.toSuccessfulResult(::StrictlyPositiveInt)
            ?: Result.failure(value shouldBe aStrictlyPositiveNumber)
    }

    override fun toString(): String = "$value"
}

/**
 * Returns this integer as a [StrictlyPositiveInt], or an
 * [IllegalArgumentException] if this integer is [negative][NegativeInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toStrictlyPositiveInt(): Result<StrictlyPositiveInt> =
    StrictlyPositiveInt of this

internal object StrictlyPositiveIntSerializer :
    KSerializer<StrictlyPositiveInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "${Package.number}.StrictlyPositiveInt",
        PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: StrictlyPositiveInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): StrictlyPositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toStrictlyPositiveInt()
            .getOrNull()
            ?: throw SerializationException(
                value shouldBe aStrictlyPositiveNumber
            )
    }
}
