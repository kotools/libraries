package kotools.types.number

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

/** Representation of negative integers including [zero][ZeroInt]. */
@Serializable(NegativeIntSerializer::class)
@SinceKotools(Types, "1.1")
public sealed interface NegativeInt : AnyInt

/**
 * Returns this integer as a [NegativeInt], or returns an
 * [IllegalArgumentException] if this integer is
 * [strictly positive][StrictlyPositiveInt].
 */
@SinceKotools(Types, "1.1")
public fun Int.toNegativeInt(): Result<NegativeInt> = when {
    this == ZeroInt.value -> Result.success(ZeroInt)
    this < ZeroInt.value -> toStrictlyNegativeInt()
    else -> Result.failure(this shouldBe aNegativeNumber)
}

internal object NegativeIntSerializer : AnyIntSerializer<NegativeInt> {
    override val serialName: Result<NotBlankString> by lazy(
        "${Package.number}.NegativeInt"::toNotBlankString
    )

    override fun deserialize(value: Int): NegativeInt = value.toNegativeInt()
        .getOrNull()
        ?: throw SerializationException(value shouldBe aNegativeNumber)
}
