import com.google.protobuf.gradle.id

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.protobuf)
    alias(libs.plugins.navigationSafeArgs)
}

android {
    namespace = "com.perqin.angumi"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.perqin.angumi"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BANGUMI_CLIENT_ID", "\"${env.BANGUMI_CLIENT_ID.value}\"")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
        }
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

// Workaround, see: https://github.com/google/ksp/issues/1288
allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.datastore)
    implementation(libs.datastore.core)
    implementation(libs.protobuf.javalite)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.navigation)
    implementation(libs.androidx.browser)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.protobuf)
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.auth)
    implementation(libs.ktor.content.negotiation)
    implementation(libs.glide)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    ksp(libs.room.compiler)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.protobuf.javalite.get().version}"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
