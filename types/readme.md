# Kotools Types

[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotools/types)](https://search.maven.org/artifact/io.github.kotools/types)
[![Kotlin](https://img.shields.io/badge/kotlin-1.5.31-blue.svg?logo=kotlin)][kotlin]

Kotools Types is a lightweight library that provides commonly used types for
[Kotlin].

> This library currently supports the _JVM_, _JS_, _Linux x64_, _macOS x64_ and
> _Windows x64_ platforms.

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

[kotlin]: https://kotlinlang.org

## Installation

### Gradle

#### Kotlin DSL

```kotlin
implementation("io.github.kotools:types:$kotoolsTypesVersion")
```

#### Groovy DSL

```groovy
implementation "io.github.kotools:types:$kotools_types_version"
```

### Maven

```xml
<dependency>
    <groupId>io.github.kotools</groupId>
    <artifactId>types</artifactId>
    <version>${kotools.types.version}</version>
</dependency>
```

## Documentation

Latest documentation of Kotools Types is available
[here](https://kotools.github.io/types).

## Contributing

Feel free to contribute to the project with
[issues](https://github.com/kotools/types/issues) and
[pull requests](https://github.com/kotools/types/pulls).

This project follows the [Conventional Commits][conventional-commits] guidelines
for committing with Git.
Please read [the specifications][conventional-commits] before committing to this
project.

[conventional-commits]: https://www.conventionalcommits.org/en/v1.0.0/

## License

This project is licensed under the
[MIT License](https://choosealicense.com/licenses/mit).
