package kotools.types.number

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.serialization.toIntSerialDescriptor
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of all integers. */
@Serializable(AnyIntSerializer::class)
@SinceKotools(Types, "4.0")
public sealed interface AnyInt : Comparable<AnyInt> {
    /** The value to hold. */
    public val value: Int

    /**
     * Compares this [value] with the [other] value for order.
     * Returns zero if this [value] equals the [other] value, a negative number
     * if it's less than the [other] value, or a positive number if it's greater
     * than the [other] value.
     */
    override fun compareTo(other: AnyInt): Int = value.compareTo(other.value)

    /** Returns this [value] as a [String]. */
    override fun toString(): String
}

internal object AnyIntSerializer : KSerializer<AnyInt> {
    override val descriptor: SerialDescriptor = "${Package.number}.AnyInt"
        .toNotBlankString()
        .map(NotBlankString::toIntSerialDescriptor)
        .getOrThrow()

    override fun serialize(encoder: Encoder, value: AnyInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): AnyInt {
        val value: Int = decoder.decodeInt()
        val result: Result<AnyInt> = when {
            value == 0 -> Result.success(ZeroInt)
            value > 0 -> value.toStrictlyPositiveInt()
            else -> value.toStrictlyNegativeInt()
        }
        return result.getOrThrow()
    }
}
