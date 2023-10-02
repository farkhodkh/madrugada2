plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceNetwork

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    //Android worker
    implementation("${Dependencies.workKtx}:${Versions.workKtx}")

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Okhttp
    implementation("${Dependencies.okhttpProfiler}:${Versions.okhttpProfiler}")
    implementation("${Dependencies.okhttLoggingInterceptor}:${Versions.okhttLoggingInterceptor}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")
    implementation("${Dependencies.gsonConverter}:${Versions.gsonConverter}")

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":data_networkapi"))
    implementation(project(":data_network-worker"))
    implementation(project(":p7lib"))
    implementation(project(":p7libapi"))
}