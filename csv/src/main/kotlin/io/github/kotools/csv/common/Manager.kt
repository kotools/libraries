package io.github.kotools.csv.common

import kotools.shared.Project.Csv
import kotools.shared.SinceKotools
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

internal val Manager.filePath: NotBlankString
    get() = "$folder$file".toNotBlankString()
        .getOrThrow()

/** Scope for manipulating CSV files. */
@SinceKotools(Csv, "2.0")
public interface Manager {
    /**
     * **Required** property for targeting a file.
     *
     * The `.csv` extension is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-file.csv"` and `"my-file"` produces the same output.
     */
    public var file: String

    /**
     * **Optional** property for targeting a folder containing the [file].
     *
     * Set to an empty string by default.
     *
     * The `'/'` suffix is optional and will be added automatically in the
     * process if not present.
     * For example, `"my-folder/"` and `"my-folder"` produces the same output.
     */
    public var folder: String

    /**
     * **Optional** property for setting the [file] content's separator.
     *
     * Set to [Separator.Comma] by default.
     */
    public var separator: Separator
}

internal abstract class ManagerImplementation : Manager {
    override var file: String by csvFile()
    override var folder: String by folder()
    override var separator: Separator = Separator.Comma
}
