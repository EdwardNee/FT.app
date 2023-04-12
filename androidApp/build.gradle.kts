plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    namespace = "app.ft.ftapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "app.ft.ftapp.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.composeLibs)
    implementation(libs.bundles.coroutines)
    implementation(libs.kodeinDi)
    // Accompanist
    implementation("com.google.accompanist:accompanist-pager:0.14.0") // Pager
    implementation("com.google.accompanist:accompanist-pager-indicators:0.14.0") // Pager Indicators
    implementation("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
//    implementation("androidx.compose.ui:ui:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
//    implementation("androidx.compose.foundation:foundation:1.2.1")
//    implementation("androidx.compose.material:material:1.2.1")
//    implementation("androidx.activity:activity-compose:1.5.1")
}