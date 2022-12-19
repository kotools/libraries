package kotools.types

import kotools.assert.assertNotNull
import kotools.assert.assertTrue

internal fun Throwable.assertHasAMessage(): Unit = message.assertNotNull()
    .isNotBlank()
    .assertTrue()
