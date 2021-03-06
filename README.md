# Nullability XJC Plugin

[![](https://jitpack.io/v/lion7/nullability-xjc-plugin.svg)](https://jitpack.io/#lion7/nullability-xjc-plugin)

## About

This plugin annotates all `getter` methods generated by XJC with `@NotNull` or `@Nullable` respectively.
Doing this allows build tooling such as the Kotlin compiler to analyze whether a value can return null (or not).

## Example usage in Gradle

```kotlin
plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.bjornvester.xjc") version "1.6.0"
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    xjc("com.github.lion7:nullability-xjc-plugin:1.0.1")
    
    implementation("org.glassfish.jaxb:jaxb-runtime:3.0.2")
}

xjc {
    options.add("-Xnullability") // enable the plugin
}
```
