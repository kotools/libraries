package io.github.kotools.csv.reader

import io.github.kotools.csv.common.Manager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotools.shared.Project.Csv
import kotools.shared.SinceKotools
import kotlin.reflect.KClass

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public suspend inline fun <reified T : Any> csvReader(
    noinline configuration: Reader<T>.() -> Unit
): List<T> = csvReader(T::class, configuration)

/**
 * Returns the file's records as a given [type] according to the given
 * [configuration] or throws an [IllegalStateException] when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public suspend fun <T : Any> csvReader(
    type: KClass<T>,
    configuration: Reader<T>.() -> Unit
): List<T> = withContext(IO) { type processReader configuration }

/**
 * Returns the file's records as a given type [T] according to the given
 * [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public suspend inline fun <reified T : Any> csvReaderOrNull(
    noinline configuration: Reader<T>.() -> Unit
): List<T>? = csvReaderOrNull(T::class, configuration)

/**
 * Returns the file's records as a given [type] according to the given
 * [configuration] or returns `null` when:
 * - the [type] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public suspend fun <T : Any> csvReaderOrNull(
    type: KClass<T>,
    configuration: Reader<T>.() -> Unit
): List<T>? = withContext(IO) { type processReaderOrNull configuration }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or throws [IllegalStateException] when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderAsync(
    noinline configuration: Reader<T>.() -> Unit
): Deferred<List<T>> = async { csvReader(configuration) }

/**
 * Returns the file's records as a given type [T] **asynchronously** according
 * to the given [configuration] or returns `null` when:
 * - the type [T] is not a public or internal data class
 * - the [configuration] is invalid
 * - the targeted file doesn't exist
 * - the targeted file's header line contains an empty string.
 */
@SinceKotools(Csv, "2.0")
public inline infix fun <reified T : Any> CoroutineScope.csvReaderOrNullAsync(
    noinline configuration: Reader<T>.() -> Unit
): Deferred<List<T>?> = async { csvReaderOrNull(configuration) }

/** Scope for reading a CSV file. */
@SinceKotools(Csv, "2.0")
public sealed interface Reader<T : Any> : Manager {
    /**
     * **Optional** function for filtering records according to the given
     * [predicate].
     */
    public fun filter(predicate: T.() -> Boolean)

    /** **Optional** function defining records pagination. */
    public fun pagination(configuration: Pagination.() -> Unit)

    /** Configurable object responsible for defining records pagination. */
    @SinceKotools(Csv, "2.1")
    public sealed interface Pagination {
        /**
         * **Required** property for setting the page number to return.
         *
         * Set to `1` by default.
         */
        public var page: Int

        /**
         * **Required** property for setting the number of items to return
         * within a [page].
         *
         * Set to `2` by default.
         */
        public var size: Int
    }
}
