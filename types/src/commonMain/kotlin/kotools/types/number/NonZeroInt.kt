package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of integers other than [zero][ZeroInt]. */
@Serializable(NonZeroIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NonZeroInt : AnyInt

/**
 * Returns this integer as a [NonZeroInt], or returns an
 * [IllegalArgumentException] if this integer equals [zero][ZeroInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toNonZeroInt(): Result<NonZeroInt> = when {
    this > ZeroInt.value -> toStrictlyPositiveInt()
    this < ZeroInt.value -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe otherThanZero)
}

internal object NonZeroIntSerializer : AnyIntSerializer<NonZeroInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NonZeroInt"::toNotBlankString
    )

    override fun deserialize(value: Int): NonZeroInt = value.toNonZeroInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe otherThanZero)
}
