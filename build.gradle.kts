buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven ("https://kotlin.bintray.com/kotlinx/")

        jcenter()
    }
    dependencies {
        classpath ("com.google.gms:google-services:3.0.0")
        classpath(libs.bundles.plugins)
        classpath(libs.mokoResGenerator)
        classpath("com.android.tools.build:gradle")
    }
}

allprojects {
    repositories {
        maven("https://jitpack.io")
//        maven ("https://kotlin.bintray.com/kotlinx/")
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.3.1").apply(false)
    id("com.android.library").version("7.3.1").apply(false)
    kotlin("android").version("1.7.10").apply(false)
    kotlin("multiplatform").version("1.7.10").apply(false)
    id("org.jetbrains.kotlin.plugin.serialization").version("1.7.10").apply(false)
    id("com.squareup.sqldelight").version("1.5.3").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
