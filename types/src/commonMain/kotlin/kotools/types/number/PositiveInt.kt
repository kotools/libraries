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

/** Representation of positive integers including [zero][ZeroInt]. */
@Serializable(PositiveIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface PositiveInt : AnyInt

/**
 * Returns this integer as a [PositiveInt], or an [IllegalArgumentException] if
 * this integer is [strictly negative][StrictlyNegativeInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toPositiveInt(): Result<PositiveInt> = when {
    this == ZeroInt.value -> Result.success(ZeroInt)
    this > ZeroInt.value -> toStrictlyPositiveInt()
    else -> Result.failure(this shouldBe aPositiveNumber)
}

internal object PositiveIntSerializer : KSerializer<PositiveInt> {
    override val descriptor: SerialDescriptor = "${Package.number}.PositiveInt"
        .toNotBlankString()
        .map(NotBlankString::toIntSerialDescriptor)
        .getOrThrow()

    override fun serialize(encoder: Encoder, value: PositiveInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): PositiveInt {
        val value: Int = decoder.decodeInt()
        return value.toPositiveInt()
            .getOrNull()
            ?: throw SerializationException(value shouldBe aPositiveNumber)
    }
}
