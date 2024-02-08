> This project is not maintained anymore.
> Please find the libraries in
> [their respective repository](https://github.com/kotools).

![Kotools](assets/banner.jpg)

<div align="center">

[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]
[![Kotools Assert](https://img.shields.io/maven-central/v/io.github.kotools/assert?color=CD6B54&label=assert)](https://search.maven.org/artifact/io.github.kotools/assert)
[![Kotools CSV](https://img.shields.io/maven-central/v/io.github.kotools/csv?color=CD6B54&label=csv)](https://search.maven.org/artifact/io.github.kotools/csv)
[![Kotools Types](https://img.shields.io/maven-central/v/io.github.kotools/types?color=CD6B54&label=types)](https://search.maven.org/artifact/io.github.kotools/types)

</div>

This project contains a set of libraries providing tools for expressive
programming in [Kotlin].

[kotlin]: https://kotlinlang.org

## Libraries

### Kotools Types

> Versions 1, 2 and 3 are not maintained anymore.
> Please find the new one in [this repository](https://github.com/kotools/types).

Kotools Types provides explicit types like `NonZeroInt` or `NotBlankString` for
the following platforms: JVM, JS, Linux x64, macOS x64 and Windows x64.

```kotlin
import kotools.types.number.StrictlyPositiveInt
import kotools.types.number.toStrictlyPositiveInt
import kotools.types.text.NotBlankString
import kotools.types.text.toNotBlankString

data class Person(val name: NotBlankString, val age: StrictlyPositiveInt)

fun main() {
    val name: NotBlankString = "Somebody".toNotBlankString()
        .getOrThrow()
    val age: StrictlyPositiveInt = 42.toStrictlyPositiveInt()
        .getOrThrow()
    val somebody = Person(name, age)
    println(somebody) // Person(name=Somebody, age=42)
}
```

### Kotools CSV

Kotools CSV is a JVM library for managing 
[CSV files](https://datatracker.ietf.org/doc/html/rfc4180) an expressive DSL.

```kotlin
data class Person(val name: String, val age: Int, val isAdmin: Boolean = false)

suspend fun main() {
    csvWriter<Person> {
        file = "people"
        records { +Person("Nobody", 25) }
    }
    val people: List<Person> = csvReader { file = "people" }
    println(people)
}
```

### Kotools Assert

Kotools Assert provides additional assertions for the following platforms: JVM,
JS, Linux x64, macOS x64 and Windows x64.

```kotlin
1 assertEquals 1
2 assertNotEquals 0

assertNull { null }
null.assertNull()

assertNotNull { 3 }
3.assertNotNull()

assertPass { println("Hello") }
assertFails { throw Exception() }
assertFailsWith<RuntimeException> { throw RuntimeException() }
```

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:csv:2.2.1")
implementation("io.github.kotools:types:3.2.0")
testImplementation("io.github.kotools:assert:3.0.2")
```

#### Groovy DSL

```groovy
implementation "io.github.kotools:csv:2.2.1"
implementation "io.github.kotools:types:3.2.0"
testImplementation "io.github.kotools:assert:3.0.2"
```

### Maven

```xml
<dependencies>
    <dependency>
        <groupId>io.github.kotools</groupId>
        <artifactId>csv</artifactId>
        <version>2.2.1</version>
    </dependency>
    <dependency>
        <groupId>io.github.kotools</groupId>
        <artifactId>types</artifactId>
        <version>3.2.0</version>
    </dependency>
    <dependency>
        <groupId>io.github.kotools</groupId>
        <artifactId>assert</artifactId>
        <version>3.0.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Documentation

Latest documentation of those libraries is available
[here](https://kotools.github.io/libraries).

## Contributing

Feel free to contribute to this project with
[issues](https://github.com/kotools/libraries/issues) and
[pull requests](https://github.com/kotools/libraries/pulls).

This project follows the [Conventional Commits][conventional-commits] guidelines
for committing with Git.
Please read [the specifications][conventional-commits] before committing to this
project.

[conventional-commits]: https://www.conventionalcommits.org/en/v1.0.0

## License

All libraries are licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
