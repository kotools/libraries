package kotools.types

import kotools.assert.assertEquals
import kotlin.test.Test

class ExplicitIntTest {
    @Test
    fun explicitInt_toNotBlankString_should_pass() {
        val explicitInts: NotEmptySet<ExplicitInt> = notEmptySetOf(
            NonZeroInt.random(),
            PositiveInt.random(),
            NegativeInt.random(),
            StrictlyPositiveInt.random(),
            StrictlyNegativeInt.random()
        )
        val x: ExplicitInt = explicitInts.random()
        val result: NotBlankString = x.toNotBlankString()
        "$result" assertEquals "$x"
    }
}
