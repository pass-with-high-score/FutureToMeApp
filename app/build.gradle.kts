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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

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

    // Unit Test
    testImplementation(libs.junit)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug Test
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}