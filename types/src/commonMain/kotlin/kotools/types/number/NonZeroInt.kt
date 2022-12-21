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

/** Representation of integers other than [zero][ZeroInt]. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NonZeroInt : AnyInt

/**
 * Returns this integer as a [NonZeroInt], or an [IllegalArgumentException] if
 * this integer equals [zero][ZeroInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = when {
    this > ZeroInt.value -> toStrictlyPositiveInt()
    this < ZeroInt.value -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe otherThanZero)
}

internal object NonZeroIntSerializer : KSerializer<NonZeroInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        "${Package.number}.NonZeroInt",
        PrimitiveKind.INT
    )

    override fun serialize(encoder: Encoder, value: NonZeroInt): Unit =
        encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): NonZeroInt {
        val value: Int = decoder.decodeInt()
        return value.toNonZeroInt()
            .getOrNull()
            ?: throw SerializationException(value shouldBe otherThanZero)
    }
}
