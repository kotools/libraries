package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.toSuccessfulResult

/**
 * Representation of lists that contain at least one element.
 *
 * @param E The type of elements contained in this list.
 */
@Serializable(NotEmptyListSerializer::class)
@SinceKotools(Types, "4.0")
public class NotEmptyList<out E>
private constructor(private val elements: List<E>) : List<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptyList<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toList()
                ?.toSuccessfulResult(::NotEmptyList)
                ?: Result.failure(EmptyCollectionException)
    }

    override fun toString(): String = "$elements"
}

/**
 * Creates a [NotEmptyList] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotools(Types, "4.0")
public fun <E> notEmptyListOf(head: E, vararg tail: E): NotEmptyList<E> {
    val elements: List<E> = listOf(head) + tail
    return elements.toNotEmptyList()
        .getOrThrow()
}

/**
 * Returns a [NotEmptyList] containing all the elements of this collection, or
 * an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotools(Types, "4.0")
public fun <E> Collection<E>.toNotEmptyList(): Result<NotEmptyList<E>> =
    NotEmptyList of this

internal class NotEmptyListSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptyList<E>> {
    private val delegate: KSerializer<List<E>> =
        ListSerializer(elementSerializer)

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = SerialDescriptor(
        "${Package.collection}.NotEmptyList",
        delegate.descriptor
    )

    override fun serialize(encoder: Encoder, value: NotEmptyList<E>): Unit =
        encoder.encodeSerializableValue(delegate, value)

    override fun deserialize(decoder: Decoder): NotEmptyList<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyList()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
