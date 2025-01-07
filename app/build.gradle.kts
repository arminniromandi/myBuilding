plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.compose)
}

android {

    signingConfigs {
        create("release") {
            storeFile = file("C:\\Users\\ARMIN141\\AndroidStudioProjects\\myBuilding\\myKey.jks")
            storePassword = "@Arminn141"
            keyAlias = "key0"
            keyPassword = "@Armin141"
        }
    }
    namespace = "ir.arminniromandi.mybuilding"
    compileSdk = 34

    defaultConfig {
        applicationId = "ir.arminniromandi.mybuilding"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation( "androidx.navigation:navigation-compose:2.7.7")

    implementation("androidx.compose.foundation:foundation:1.4.1")

    implementation("com.google.accompanist:accompanist-pager-indicators:0.30.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1")




    val room_version = "2.6.1"
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")


    val lifecycle_version = "2.6.1"
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    implementation ("com.google.code.gson:gson:2.10.1")


}