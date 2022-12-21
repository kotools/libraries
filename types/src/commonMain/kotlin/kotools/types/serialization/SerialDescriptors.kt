package kotools.types.serialization

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotools.types.text.NotBlankString

private class IntSerialDescriptor(name: NotBlankString) :
    SerialDescriptor by PrimitiveSerialDescriptor(name.value, PrimitiveKind.INT)

internal fun NotBlankString.toIntSerialDescriptor(): SerialDescriptor =
    IntSerialDescriptor(this)

private class StringSerialDescriptor(name: NotBlankString) :
    SerialDescriptor by PrimitiveSerialDescriptor(
        name.value,
        PrimitiveKind.STRING
    )

internal fun NotBlankString.toStringSerialDescriptor(): SerialDescriptor =
    StringSerialDescriptor(this)
