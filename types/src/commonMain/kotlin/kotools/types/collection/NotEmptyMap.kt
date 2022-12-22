package kotools.types.collection

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotools.shared.Project.Types
import kotools.shared.SinceKotools
import kotools.types.Package
import kotools.types.toSuccessfulResult

/**
 * Representation of maps that contain at least one entry.
 *
 * @param K The type of map keys.
 * @param V The type of map values.
 */
@Serializable(NotEmptyMapSerializer::class)
@SinceKotools(Types, "4.0")
public class NotEmptyMap<K, out V>
private constructor(private val map: Map<K, V>) : Map<K, V> by map {
    internal companion object {
        infix fun <K, V> of(map: Map<K, V>): Result<NotEmptyMap<K, V>> = map
            .takeIf(Map<K, V>::isNotEmpty)
            ?.toSuccessfulResult(::NotEmptyMap)
            ?: Result.failure(EmptyMapException)
    }

    override fun toString(): String = "$map"
}

/**
 * Creates a [NotEmptyMap] starting with a [head] and containing all the entries
 * of the optional [tail].
 */
@SinceKotools(Types, "4.0")
public fun <K, V> notEmptyMapOf(
    head: Pair<K, V>,
    vararg tail: Pair<K, V>
): NotEmptyMap<K, V> = mapOf(head, *tail)
    .toNotEmptyMap()
    .getOrThrow()

/**
 * Returns a [NotEmptyMap] containing all the entries of this map, or an
 * [IllegalArgumentException] if this map is empty.
 */
@SinceKotools(Types, "4.0")
public fun <K, V> Map<K, V>.toNotEmptyMap(): Result<NotEmptyMap<K, V>> =
    NotEmptyMap of this

internal class NotEmptyMapSerializer<K, V>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>
) : KSerializer<NotEmptyMap<K, V>> {
    private val delegate: KSerializer<Map<K, V>> =
        MapSerializer(keySerializer, valueSerializer)

    @ExperimentalSerializationApi
    override val descriptor: SerialDescriptor = SerialDescriptor(
        "${Package.collection}.NotEmptyMap",
        delegate.descriptor
    )

    override fun serialize(encoder: Encoder, value: NotEmptyMap<K, V>): Unit =
        encoder.encodeSerializableValue(delegate, value)

    override fun deserialize(decoder: Decoder): NotEmptyMap<K, V> = decoder
        .decodeSerializableValue(delegate)
        .toNotEmptyMap()
        .getOrNull()
        ?: throw SerializationException(EmptyMapException)
}

private object EmptyMapException :
    IllegalArgumentException("Given map shouldn't be empty.")
