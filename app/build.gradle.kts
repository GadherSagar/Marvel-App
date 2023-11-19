import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.marvelapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marvelapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        all {

            val propertyFile = project.file("marvel_app.properties")
            if (propertyFile.exists()) {
                val properties = Properties()
                properties.load(FileInputStream(propertyFile))
                val baseUrl: String = properties.getProperty("BASE_URL")
                val privateKey: String = properties.getProperty("PUBLIC_KEY")
                val publicKey: String = properties.getProperty("PRIVATE_KEY")

                buildConfigField(
                    type = "String",
                    name = "BASE_URL",
                    value = "\"" + baseUrl + "\""
                )
                buildConfigField(
                    type = "String",
                    name = "PUBLIC_KEY",
                    value = "\"" + privateKey + "\""
                )
                buildConfigField(
                    type = "String",
                    name = "PRIVATE_KEY",
                    value = "\"" + publicKey + "\""
                )
            } else {
                throw FileNotFoundException()
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.core.splashscreen)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    ksp(libs.androidx.lifecycle.compiler)

    implementation(libs.androidx.room)
    ksp(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // https://github.com/square/retrofit/tags
    implementation(libs.retrofit2.retrofit)
    // https://github.com/square/retrofit/tree/master/retrofit-converters/moshi
    implementation(libs.retrofit2.converter.moshi)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.codegen)

    // https://square.github.io/okhttp/#releases
    implementation(platform(libs.okhttp3.okHttpBom))
    api(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.coil.kt)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}