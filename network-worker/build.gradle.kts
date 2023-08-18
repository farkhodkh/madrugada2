plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespaceNetworkWorker
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
}

dependencies {

    //Android Core
    implementation("${Dependencies.coreKtx}:${Versions.coreKtx}")

    //Android worker
    implementation("${Dependencies.workKtx}:${Versions.workKtx}")

    //coroutines dependencies
    implementation("${Dependencies.kotlinxCoroutines}:${Versions.kotlinxCoroutines}")
    implementation("${Dependencies.kotlinxCoroutinesCore}:${Versions.kotlinxCoroutines}")

    //Okhttp
    implementation("${Dependencies.okhttpProfiler}:${Versions.okhttpProfiler}")
    implementation("${Dependencies.okhttLoggingInterceptor}:${Versions.okhttLoggingInterceptor}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")

    //Modules
    implementation(project(":networkapi"))
    implementation(project(":p7libapi"))
}