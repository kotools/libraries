package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.toSuccessfulResult

/**
 * Representation of sets that contain at least one element.
 *
 * @param E The type of elements contained in this set.
 */
@Serializable(NotEmptySetSerializer::class)
@SinceKotools(Types, "4.0")
public class NotEmptySet<out E>
private constructor(private val elements: Set<E>) : Set<E> by elements {
    internal companion object {
        infix fun <E> of(elements: Collection<E>): Result<NotEmptySet<E>> =
            elements.takeIf(Collection<E>::isNotEmpty)
                ?.toSet()
                ?.toSuccessfulResult(::NotEmptySet)
                ?: Result.failure(EmptyCollectionException)
    }

    override fun toString(): String = "$elements"
}

/**
 * Creates a [NotEmptySet] starting with a [head] and containing all the
 * elements of the optional [tail].
 */
@SinceKotools(Types, "4.0")
public fun <E> notEmptySetOf(head: E, vararg tail: E): NotEmptySet<E> {
    val result: List<E> = listOf(head) + tail
    return result.toNotEmptySet()
        .getOrThrow()
}

/**
 * Returns a [NotEmptySet] containing all the elements of this collection, or
 * an [IllegalArgumentException] if this collection is empty.
 */
@SinceKotools(Types, "4.0")
public fun <E> Collection<E>.toNotEmptySet(): Result<NotEmptySet<E>> =
    NotEmptySet of this

internal class NotEmptySetSerializer<E>(elementSerializer: KSerializer<E>) :
    KSerializer<NotEmptySet<E>> {
    private val delegate: KSerializer<Set<E>> = SetSerializer(elementSerializer)

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = SerialDescriptor(
        "${Package.collection}.NotEmptySet",
        delegate.descriptor
    )

    override fun serialize(encoder: Encoder, value: NotEmptySet<E>): Unit =
        encoder.encodeSerializableValue(delegate, value)

    override fun deserialize(decoder: Decoder): NotEmptySet<E> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptySet()
        .getOrNull()
        ?: throw SerializationException(EmptyCollectionException)
}
