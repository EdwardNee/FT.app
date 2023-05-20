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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.composeLibs)
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.maxkeeperLibs)
    implementation(libs.kodeinDi)
    implementation("androidx.paging:paging-common-ktx:3.1.1")
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.0-alpha")
//    implementation("com.whiteelephant:monthandyearpicker:1.3.0")
    implementation("com.github.hseapp:HseAuth-Android:1.0.1")
    implementation("com.github.hseapp:HSECore-Android:1.0.7")
    implementation("com.google.dagger:dagger:2.42")
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    kapt("com.google.dagger:dagger-compiler:2.42")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.yandex.android:maps.mobile:4.3.1-full")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.31.1-alpha")


//    implementation("androidx.work:work-runtime-ktx::2.7.1")

//    implementation("androidx.compose.ui:ui:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
//    implementation("androidx.compose.foundation:foundation:1.2.1")
//    implementation("androidx.compose.material:material:1.2.1")
//    implementation("androidx.activity:activity-compose:1.5.1")
}