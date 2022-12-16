package kotools.csv

import kotools.shared.Project.Csv
import kotools.shared.SinceKotools
import kotools.types.NotBlankString
import kotools.types.toNotBlankString

/** Representation of a path pointing to a CSV file. */
@JvmInline
@SinceKotools(Csv, "2.3")
public value class CsvPath
private constructor(private val value: NotBlankString) {
    internal companion object {
        val extension: NotBlankString by lazy(
            ".csv".toNotBlankString()::getOrThrow
        )

        infix fun of(value: NotBlankString): Result<CsvPath> = value
            .takeIf { "$it" != "$extension" }
            ?.let { "$it.csv" }
            ?.toNotBlankString()
            ?.getOrThrow()
            ?.let(::CsvPath)
            ?.let(Result.Companion::success)
            ?: Result.failure(
                IllegalArgumentException(
                    "Given path shouldn't equal the CSV extension."
                )
            )
    }

    /** Returns this path as a [String]. */
    override fun toString(): String = "$value"
}

/**
 * Returns this string as a [CsvPath], or an [IllegalArgumentException] if this
 * string equals the `.csv` extension.
 */
@SinceKotools(Csv, "2.3")
public fun NotBlankString.toCsvPath(): Result<CsvPath> = CsvPath of this
