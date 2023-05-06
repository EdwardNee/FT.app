plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization")

    id("com.android.library")
    id("kotlin-parcelize")
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.squareup.sqldelight")
//    id("dev.icerock.moko.kswift")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "MultiPlatformLibrary"
            isStatic = false
            export(libs.mokoRes)
            export(libs.mokoMvvmCore)
            export(libs.mokoMvvmFlow)
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.bundles.ktorClient)
                implementation(libs.ktxDateTime)
                implementation(libs.kodeinDi)
                implementation(libs.sqldelightRuntime)
                api(libs.mokoRes)
                api(libs.bundles.mokoMvvmCommon)

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.kodeinDi)
                implementation(libs.sqldelightAndroid)
                implementation(libs.mokoResAndroid)
                implementation(libs.mokoResCompose)
                api(libs.bundles.mokoMvvmAndroidApi)
                implementation("com.yandex.android:maps.mobile:4.3.1-full")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.sqldelightNative)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    sourceSets["main"].res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    namespace = "app.ft.ftapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 29
        targetSdk = 33
    }
}

sqldelight {
    database("FTAppDatabase") {
        packageName = "db"
        sourceFolders = listOf("sqldelight")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "app.ft.ftapp"
}

dependencies {
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    commonMainApi(libs.mokoRes)
    commonMainImplementation("dev.icerock.moko:parcelize:0.4.0")
    commonMainImplementation("dev.icerock.moko:graphics:0.4.0")
}