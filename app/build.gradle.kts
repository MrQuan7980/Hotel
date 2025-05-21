plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.dagger.hilt)
}

android {
    namespace = "com.example.appbookinghotel"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appbookinghotel"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding{
        enable = true
    }
    packagingOptions {
        pickFirst("META-INF/LICENSE.txt")
        exclude ("META-INF/NOTICE.md")
        exclude ("META-INF/LICENSE.md")
    }
}

dependencies {
    implementation(project(":feature_admin"))
    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    implementation(libs.api.gson)
    implementation(libs.squareup.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.glide)
    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.compiler.glide)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.activity.ktx)

    // storeData
    implementation(libs.datastore.preferences)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // thư viện Email
    implementation(libs.android.email)
    implementation(libs.android.email.activation)

    // thư viện hilt
    implementation(libs.android.hilt.android)
    kapt(libs.android.hilt.android.compiler)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
}