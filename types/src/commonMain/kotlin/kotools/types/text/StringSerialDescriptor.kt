package kotools.types.text

import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor

private class StringSerialDescriptor(name: NotBlankString) :
    SerialDescriptor by PrimitiveSerialDescriptor(
        name.value,
        PrimitiveKind.STRING
    )

internal fun Result<NotBlankString>.toStringSerialDescriptor():
        Result<SerialDescriptor> = map(::StringSerialDescriptor)
