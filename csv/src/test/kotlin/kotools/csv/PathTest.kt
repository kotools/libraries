package kotools.csv

import kotools.assert.assertEquals
import kotools.assert.assertFailsWith
import kotools.assert.assertNotNull
import kotools.assert.assertTrue
import kotools.types.toNotBlankString
import org.junit.jupiter.api.Test

class PathTest {
    @Test
    fun notBlankString_csv_should_pass_with_a_NotBlankString_other_than_the_extension() {
        val file = "file"
        file.toNotBlankString()
            .getOrThrow()
            .toCsvPath()
            .getOrThrow()
            .toString() assertEquals "$file${CsvPath.extension}"
    }

    @Test
    fun notBlankString_csv_should_fail_with_a_NotBlankString_that_equals_the_extension() {
        val result: Result<CsvPath> = CsvPath.extension.toCsvPath()
        assertFailsWith<IllegalArgumentException>(result::getOrThrow)
            .message
            .assertNotNull()
            .isNotBlank()
            .assertTrue()
    }
}
