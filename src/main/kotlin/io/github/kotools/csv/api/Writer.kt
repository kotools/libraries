package io.github.kotools.csv.api

import io.github.kotools.csv.core.writer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Creates a [writer][Writer] with the given [config] and writes new
 * [rows][Writer.rows] in the corresponding [file][Csv.file]'s.
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public suspend fun csvWriter(config: Writer.() -> Unit): Unit? =
    withContext(IO) { writer(config) }

/**
 * Creates a [writer][Writer] with the given [config] and writes new
 * [rows][Writer.rows] in the corresponding [file][Csv.file]'s
 * **asynchronously**.
 *
 * Returns `null` if the [config] is invalid or if something goes wrong in the
 * process.
 */
public infix fun CoroutineScope.csvWriterAsync(
    config: Writer.() -> Unit
): Deferred<Unit?> = async(IO) { writer(config) }

/** Scope for writing in CSV files. */
public interface Writer : Csv {
    /** **Required** property for defining the [file]'s header. */
    public var header: Set<String>

    /**
     * **Optional** flag for overwriting the [file]'s content.
     *
     * Set to `true` by default.
     */
    public var overwrite: Boolean

    /**
     * **Required** function that defines the rows to write with the given
     * [def] block.
     */
    public infix fun rows(def: Rows.() -> Unit)

    /** Scope for defining the [rows] to write. */
    public interface Rows : Csv {
        /** Adds the given [iterable][Iterable] as a row. */
        public operator fun Iterable<String>.unaryPlus()
    }
}