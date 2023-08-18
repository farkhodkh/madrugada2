plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespaceNetwork
    compileSdkVersion = Versions.compileSdkVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdk

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
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
//
//    buildFeatures {
//        compose = true
//    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
//    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
//    implementation("${Dependencies.composeRuntime}:${Versions.compose}")

    //Okhttp
    implementation("${Dependencies.okhttpProfiler}:${Versions.okhttpProfiler}")
    implementation("${Dependencies.okhttLoggingInterceptor}:${Versions.okhttLoggingInterceptor}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")

    //Modules
    implementation(project(":util"))
    implementation(project(":networkapi"))

}