package kotools.csv

import kotools.types.string.NotBlankString
import kotools.types.string.toNotBlankString
import kotools.types.string.toNotBlankStringOrNull

/**
 * Returns this string as a [CSV path][CsvPathResult.Success] suffixed with the
 * `.csv` extension, or returns a [CsvPathResult.Exception.CsvExtensionAsPath]
 * if this string equals the `.csv` extension.
 */
@SinceKotoolsCsv("2.3")
public fun NotBlankString.csv(): CsvPathResult.FromNotBlankString =
    csvImplementation() as CsvPathResult.FromNotBlankString

/**
 * Returns this string as a [CSV path][CsvPathResult.Success] suffixed with the
 * `.csv` extension, or returns a [CsvPathResult.Exception.BlankString] when
 * this string is blank, or returns a
 * [CsvPathResult.Exception.CsvExtensionAsPath] when this string equals the
 * `.csv` extension.
 */
@SinceKotoolsCsv("2.3")
public fun String.csv(): CsvPathResult.FromString = toNotBlankStringOrNull()
    ?.run { csvImplementation() as CsvPathResult.FromString }
    ?: CsvPathResult.Exception.BlankString

private fun NotBlankString.csvImplementation(): CsvPathResult =
    if (value == csvExtension.toString())
        CsvPathResult.Exception.CsvExtensionAsPath
    else CsvPathResult.Success(this suffixWith csvExtension)

/**
 * Returns this string as a [CSV path][CsvPathResult.Success] suffixed with the
 * `.csv` extension, or returns `null` if this string equals the `.csv`
 * extension.
 */
@SinceKotoolsCsv("2.3")
public fun NotBlankString.csvOrNull(): CsvPathResult.Success? = csv()
    .onException { return null }

internal inline fun CsvPathResult.onException(
    action: (CsvPathResult.Exception) -> CsvPathResult.Success
): CsvPathResult.Success = when (this) {
    is CsvPathResult.Success -> this
    is CsvPathResult.Exception -> action(this)
}

/** Object returned when trying to build a [CSV path][CsvPathResult.Success]. */
@SinceKotoolsCsv("2.3")
public sealed interface CsvPathResult {
    /**
     * Object returned when trying to build a [CSV path][Success] from a
     * [NotBlankString].
     */
    public sealed interface FromNotBlankString : CsvPathResult

    /**
     * Object returned when trying to build a [CSV path][Success] from a
     * [String].
     */
    public sealed interface FromString : CsvPathResult

    /** Representation of a path pointing to a CSV file. */
    @JvmInline
    public value class Success
    internal constructor(internal val path: NotBlankString) :
        FromNotBlankString,
        FromString

    /** Object returned when building a [CSV path][Success] fails. */
    public sealed class Exception(reason: NotBlankString) :
        IllegalArgumentException("CSV path shouldn't $reason."),
        CsvPathResult {
        /**
         * Exception returned when trying to build a [CSV path][Success] from a
         * blank string.
         */
        public object BlankString : Exception("be blank".toNotBlankString()),
            FromString

        /**
         * Exception returned when trying to build a [CSV path][Success] from a
         * string that equals the `.csv` extension.
         */
        public object CsvExtensionAsPath :
            Exception(
                "equal the \"$csvExtension\" extension".toNotBlankString()
            ),
            FromNotBlankString,
            FromString
    }
}
