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

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")

    //Okhttp
    implementation("${Dependencies.okhttpProfiler}:${Versions.okhttpProfiler}")
    implementation("${Dependencies.okhttLoggingInterceptor}:${Versions.okhttLoggingInterceptor}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")

    //Modules
    implementation(project(":util"))
    implementation(project(":networkapi"))

}