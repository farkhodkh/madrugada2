plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
    id("ru.petrolplus.pos.compose")
}

android {

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    //Lib
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*aar"))))

    //Core
    implementation("${Dependencies.coreKtx}:${Versions.coreKtx}")
    implementation("${Dependencies.appcompat}:${Versions.appcompat}")
    implementation("${Dependencies.annotation}:${Versions.annotation}")
    implementation("${Dependencies.androidMaterial}:${Versions.androidMaterial}")

    //Json
    implementation("${Dependencies.gson}:${Versions.gson}")

    //Modules
    implementation(project(":util"))
    implementation(project(":persistence"))
    implementation(kotlin("reflect"))
}