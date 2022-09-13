import org.jetbrains.dokka.Platform
import org.jetbrains.dokka.gradle.GradleDokkaSourceSetBuilder
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl

plugins {
    kotlin("multiplatform") version embeddedKotlinVersion
    kotlin("plugin.serialization") version embeddedKotlinVersion
    id("org.jetbrains.dokka") version embeddedKotlinVersion
    `maven-publish`
    signing
}

group = "io.github.kotools"
version = "3.0.0-SNAPSHOT"

repositories(RepositoryHandler::mavenCentral)

val isSnapshot: Boolean by lazy { version.toString().endsWith("SNAPSHOT") }
lateinit var commonSourceSet: KotlinSourceSet
lateinit var jvmSourceSet: KotlinSourceSet
lateinit var jsSourceSet: KotlinSourceSet
lateinit var nativeSourceSet: KotlinSourceSet
lateinit var linuxSourceSet: KotlinSourceSet
lateinit var macosSourceSet: KotlinSourceSet
lateinit var windowsSourceSet: KotlinSourceSet

java.targetCompatibility = JavaVersion.VERSION_1_8

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        testRuns["test"].executionTask.configure(Test::useJUnitPlatform)
    }
    js(BOTH, KotlinJsTargetDsl::browser)
    linuxX64()
    macosX64()
    mingwX64()
    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain: KotlinSourceSet by getting {
            dependencies {
                implementation(project.dependencies.platform(kotlin("bom")))
                implementation(
                    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1"
                )
            }
        }
        val commonTest: KotlinSourceSet by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.github.kotools:assert:[3.0,3.1[")
            }
        }
        commonSourceSet = commonMain
        val jsMain: KotlinSourceSet by getting
        jsSourceSet = jsMain
        val jvmMain: KotlinSourceSet by getting
        val jvmTest: KotlinSourceSet by getting
        jvmSourceSet = jvmMain
        val nativeMain: KotlinSourceSet by creating { dependsOn(commonMain) }
        val nativeTest: KotlinSourceSet by creating { dependsOn(commonTest) }
        nativeSourceSet = nativeMain
        val linuxX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val linuxX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        linuxSourceSet = linuxX64Main
        val macosX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val macosX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        macosSourceSet = macosX64Main
        val mingwX64Main: KotlinSourceSet by getting { dependsOn(nativeMain) }
        val mingwX64Test: KotlinSourceSet by getting { dependsOn(nativeTest) }
        windowsSourceSet = mingwX64Main
    }
}

// ---------- Tasks ----------

tasks.withType<Jar> {
    fun key(suffix: String): String = "Implementation-$suffix"
    val name: Pair<String, String> = key("Title") to project.name
    val version: Pair<String, Any> = key("Version") to project.version
    manifest.attributes(name, version)
}

val dokkaDirectory: File = buildDir.resolve("dokka")
tasks.dokkaHtml {
    outputDirectory.set(dokkaDirectory)
    dokkaSourceSets {
        fun GradleDokkaSourceSetBuilder.sourceRoots(
            vararg sourceSets: KotlinSourceSet
        ): Unit = sourceSets.forEach { sourceRoots.from(it.kotlin.srcDirs) }

        configureEach {
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
        }
        named(commonSourceSet.name) { displayName.set("All platforms") }
        named(jsSourceSet.name) {
            displayName.set("JS")
            platform.set(Platform.js)
            dependsOn(commonSourceSet.name)
            sourceRoots(jsSourceSet)
        }
        named(jvmSourceSet.name) {
            displayName.set("JVM")
            platform.set(Platform.jvm)
            dependsOn(commonSourceSet.name)
            sourceRoots(jvmSourceSet)
        }
        named(nativeSourceSet.name) {
            displayName.set("Native")
            platform.set(Platform.native)
            dependsOn(commonSourceSet.name)
            sourceRoots(
                nativeSourceSet,
                linuxSourceSet,
                macosSourceSet,
                windowsSourceSet
            )
        }
    }
}
val cleanDokkaHtml: TaskProvider<Delete> =
    tasks.register<Delete>("cleanDokkaHtml") { delete(dokkaDirectory) }

val javadocJar: TaskProvider<Jar> = tasks.register<Jar>("javadocJar") {
    dependsOn(cleanDokkaHtml, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

tasks.assemble { dependsOn(javadocJar) }

tasks.withType<Sign> {
    onlyIf {
        val taskNames: List<String> = project.gradle.startParameter.taskNames
        val isPublishingToMavenLocal: Boolean =
            tasks.publishToMavenLocal.name in taskNames
        !isSnapshot && !isPublishingToMavenLocal
    }
}

// ---------- Publishing & signing ----------

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/kotools/types")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            val uriSuffix: String =
                if (isSnapshot) "content/repositories/snapshots/"
                else "service/local/staging/deploy/maven2/"
            url = uri("https://s01.oss.sonatype.org/$uriSuffix")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
    }
    publications {
        @Suppress("UNUSED_VARIABLE")
        val kotlinMultiplatform by getting(MavenPublication::class) {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
        }
    }
    publications.forEach {
        if (it !is MavenPublication) return@forEach
        it.artifact(javadocJar)
        it.pom {
            name.set("Kotools Types")
            description.set("Commonly used types for Kotlin.")
            val gitRepository = "https://github.com/kotools/types"
            url.set(gitRepository)
            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                }
            }
            issueManagement {
                system.set("GitHub")
                url.set("$gitRepository/issues")
            }
            scm {
                connection.set("$gitRepository.git")
                url.set(gitRepository)
            }
            developers {
                developer {
                    name.set(System.getenv("GIT_USER"))
                    email.set(System.getenv("GIT_EMAIL"))
                }
            }
        }
        signing {
            val secretKey: String? = System.getenv("GPG_PRIVATE_KEY")
            val password: String? = System.getenv("GPG_PASSWORD")
            useInMemoryPgpKeys(secretKey, password)
            sign(it)
        }
    }
}

System.getenv("OS")?.let {
    setOf("GitHubRepository", "OSSRHRepository").forEach { repository: String ->
        tasks.getByName("publishAllPublicationsTo$repository") {
            val platforms: MutableList<String> = mutableListOf()
            when {
                it.startsWith("macos") -> platforms += "MacosX64"
                it.startsWith("windows") -> platforms += "MingwX64"
                it.startsWith("ubuntu") ->
                    setOf("KotlinMultiplatform", "Js", "Jvm", "LinuxX64")
                        .forEach { platforms += it }
            }
            val paths: Array<Task> = platforms.map { prefix: String ->
                tasks.getByName("publish${prefix}PublicationTo$repository")
            }.toTypedArray()
            dependsOn(*paths)
        }
    }
}
