package kotools.types.number

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotools.types.text.NotBlankString

private class IntSerialDescriptor(name: NotBlankString) :
    SerialDescriptor by PrimitiveSerialDescriptor(name.value, PrimitiveKind.INT)

internal fun Result<NotBlankString>.toIntSerialDescriptor():
        Result<SerialDescriptor> = map(::IntSerialDescriptor)
