plugins {
    kotlin("android")
    id("com.android.application")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt").version("1.23.0-RC3")
}

detekt {
    // Version of Detekt that will be used. When unspecified the latest detekt
    // version found will be used. Override to stay on the same version.
    toolVersion = "1.23.0-RC3"

    // The directories where detekt looks for source files.
    // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
    source = files("src/main/java", "src/main/kotlin")

    // Builds the AST in parallel. Rules are always executed in parallel.
    // Can lead to speedups in larger projects. `false` by default.
    parallel = false


    // Define the detekt configuration(s) you want to use.
    // Defaults to the default detekt configuration.
    config = files("../config/detekt.yml")

    // Applies the config files on top of detekt's default config file. `false` by default.
    buildUponDefaultConfig = false

    // Turns on all the rules. `false` by default.
    allRules = false

    // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
//    baseline = file("../config/detekt-baseline.xml")

    // Disables all default detekt rulesets and will only run detekt with custom rules
    // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
    disableDefaultRuleSets = false

    // Adds debug output during task execution. `false` by default.
    debug = false

    // If set to `true` the build does not fail when the
    // maxIssues count was reached. Defaults to `false`.
    ignoreFailures = false

    // Android: Don't create tasks for the specified build types (e.g. "release")
    ignoredBuildTypes = listOf("release")

    // Android: Don't create tasks for the specified build flavor (e.g. "production")
    ignoredFlavors = listOf("production")

    // Android: Don't create tasks for the specified build variants (e.g. "productionRelease")
    ignoredVariants = listOf("productionRelease")

    // Specify the base path for file paths in the formatted reports.
    // If not set, all file paths reported will be absolute file path.
//    basePath = projectDir
}

android {
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    namespace = "app.ft.ftapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "app.ft.ftapp.android"
        minSdk = 29
        targetSdk = 33
        versionCode = 5
        versionName = "1.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
//    implementation("io.gitlab.arturbosch.detekt:detekt-rules-libraries:1.22.0")
//    implementation("io.gitlab.arturbosch.detekt:detekt-rules-ruleauthors:1.22.0")
//    implementation("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")

    debugImplementation(libs.composeUiTests)
    debugImplementation(libs.composeUiTestsManifest)
    debugImplementation(libs.composeNavigationTesting)

//    implementation("androidx.work:work-runtime-ktx::2.7.1")

//    implementation("androidx.compose.ui:ui:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling:1.2.1")
//    implementation("androidx.compose.ui:ui-tooling-preview:1.2.1")
//    implementation("androidx.compose.foundation:foundation:1.2.1")
//    implementation("androidx.compose.material:material:1.2.1")
//    implementation("androidx.activity:activity-compose:1.5.1")
}