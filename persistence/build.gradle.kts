plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petroplus.pos.convention")
}

android {
    namespace = Dependencies.namespacePersistence

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    //Modules
    implementation(project(":room"))
    implementation(project(":core"))
    implementation(project(":util"))

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Room for testing
    androidTestImplementation("${Dependencies.roomKtx}:${Versions.room}")

    //Testing
    testImplementation("${Dependencies.junit}:${Versions.junit}")
    androidTestImplementation("${Dependencies.extJunit}:${Versions.extJunit}")
    androidTestImplementation("${Dependencies.espresso}:${Versions.espresso}")
}