import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.futureus.futuretome"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.futureus.futuretome"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val supabaseAnoKey: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_ANON_KEY") ?: ""
        val supabaseUrl: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_URL") ?: ""
        val supabaseRole: String = gradleLocalProperties(rootDir, providers).getProperty("SUPABASE_ROLE") ?: ""
        val secret: String = gradleLocalProperties(rootDir, providers).getProperty("SECRET") ?: ""
        buildConfigField("String", "API_KEY", "\"$supabaseAnoKey\"")
        buildConfigField("String", "SUPABASE_URL", "\"$supabaseUrl\"")
        buildConfigField("String", "SUPABASE_ROLE", "\"$supabaseRole\"")
        buildConfigField("String", "SECRET", "\"$secret\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Compose
    implementation(libs.bundles.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Serialization
    implementation(libs.bundles.serialization)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.work)

    // Superbase
    implementation(libs.bundles.supabase)

    // Ktor
    implementation(libs.bundles.ktor)

    // Coil
    implementation(libs.coil.kt.coil.compose)

    // Compose Destination
    implementation(libs.bundles.compose.destinations)

    // Paging Compose
    implementation(libs.bundles.paging.compose)

    // RichEditor
    implementation(libs.rich.editor)

    // Unit Test
    testImplementation(libs.bundles.testing)

    // Android Test
    androidTestImplementation(libs.bundles.android.testing)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Debug Test
    debugImplementation(libs.bundles.debugging)
}