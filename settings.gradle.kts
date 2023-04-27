pluginManagement {
    repositories {
        maven("https://jitpack.io")
        maven ("https://kotlin.bintray.com/kotlinx/")
        google()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
        maven ("https://kotlin.bintray.com/kotlinx/")
        maven ("https://dl.bintray.com/florent37/maven")
        gradlePluginPortal()
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "FTapp"
include(":androidApp")
include(":shared")
