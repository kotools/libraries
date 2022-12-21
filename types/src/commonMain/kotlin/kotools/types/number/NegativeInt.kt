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

/** Representation of negative integers including [zero][ZeroInt]. */
@Serializable(NegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NegativeInt : AnyInt

/**
 * Returns this integer as a [NegativeInt], or an [IllegalArgumentException] if
 * this integer is [strictly positive][StrictlyPositiveInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toNegativeInt(): Result<NegativeInt> = when {
    this == ZeroInt.value -> Result.success(ZeroInt)
    this < ZeroInt.value -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe aNegativeNumber)
}

internal object NegativeIntSerializer : KSerializer<NegativeInt> {
    override val descriptor: SerialDescriptor = "${Package.number}.NegativeInt"
        .toNotBlankString()
        .map(NotBlankString::toIntSerialDescriptor)
        .getOrThrow()

    override fun serialize(encoder: Encoder, value: NegativeInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): NegativeInt {
        val value: Int = decoder.decodeInt()
        return value.toNegativeInt()
            .getOrNull()
            ?: throw SerializationException(value shouldBe aNegativeNumber)
    }
}
