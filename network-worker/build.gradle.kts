plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceNetworkWorker

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
    implementation(project(":core"))
}