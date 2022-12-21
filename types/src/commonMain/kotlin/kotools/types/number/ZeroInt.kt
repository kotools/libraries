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

/** Representation of the zero integer. */
@Serializable(ZeroIntSerializer::class)
@SinceKotools(Types, "4.0")
public object ZeroInt : PositiveInt, NegativeInt {
    override val value: Int = 0

    /**
     * Returns `true` if the [other] value is a [ZeroInt] or `false` otherwise.
     */
    override fun equals(other: Any?): Boolean = other is ZeroInt

    /** Returns the hash code of this [value]. */
    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "$value"
}

internal object ZeroIntSerializer : KSerializer<ZeroInt> {
    override val descriptor: SerialDescriptor = "${Package.number}.ZeroInt"
        .toNotBlankString()
        .map(NotBlankString::toIntSerialDescriptor)
        .getOrThrow()

    override fun serialize(encoder: Encoder, value: ZeroInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): ZeroInt {
        val value: Int = decoder.decodeInt()
        if (value != 0) throw SerializationException(
            "Unable to deserialize $value to ZeroInt."
        )
        return ZeroInt
    }
}
