package kotools.types

/**
 * Returns the value of the environment variable corresponding to the given
 * [name], or returns `null` if no environment variable exists with the given
 * [name].
 */
fun env(name: String): String? = System.getenv(name)

/** Represents a group of environment variables. */
sealed class EnvironmentGroup(private val prefix: String? = null) {
    /**
     * Returns the value of the environment variable corresponding to the
     * given [name], or returns `null` if no environment variable exists with
     * the given [name].
     *
     * If a [prefix] is defined, this function will search an environment
     * variable that has a name matching the concatenation of the [prefix] and
     * the given [name].
     * For example, given a [prefix] that equals `HELLO` and a [name] that
     * equals `WORLD`, this function will search for an environment variable
     * called `HELLO_WORLD`.
     */
    internal infix operator fun get(name: String): String? {
        val key: String = prefix?.let { "${it}_$name" } ?: name
        return env(key)
    }
}

object Git : EnvironmentGroup("GIT") {
    val email: String? by lazy { this["EMAIL"] }
    val user: String? by lazy { this["USER"] }
}

object GitHub : EnvironmentGroup("GITHUB") {
    val password: String? by lazy { this["TOKEN"] }
    val username: String? by lazy { this["ACTOR"] }
}

object Gpg : EnvironmentGroup("GPG") {
    val password: String? by lazy { this["PASSWORD"] }
    val secretKey: String? by lazy { this["PRIVATE_KEY"] }
}

object Maven : EnvironmentGroup("MAVEN") {
    val password: String? by lazy { this["PASSWORD"] }
    val username: String? by lazy { this["USERNAME"] }
}