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

- [Kotools Assert](https://github.com/kotools/libraries/blob/main/assert/readme.md)
  is a multiplatform library providing lightweight assertions.
- [Kotools CSV](https://github.com/kotools/libraries/blob/main/csv/readme.md) is
  a JVM library for managing CSV files with an elegant DSL.
- [Kotools Types](https://github.com/kotools/libraries/blob/main/types/readme.md)
  is a multiplatform library that provides commonly used types like `NonZeroInt`
  or `NotEmptyList`.

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
