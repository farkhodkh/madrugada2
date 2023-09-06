//TODO - Этот модуль нужно вернуть в java-lib
//Убрать класс File из API

plugins {
//    id("java-library")
//    id("org.jetbrains.kotlin.jvm")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
}

android {
    namespace = Dependencies.namespaceP7LibApi

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_1_8
//    targetCompatibility = JavaVersion.VERSION_1_8
//}

dependencies {
    //Core
//    implementation("${Dependencies.coreKtx}:${Versions.coreKtx}")
//    implementation("${Dependencies.appcompat}:${Versions.appcompat}")
//    implementation("${Dependencies.annotation}:${Versions.annotation}")
//    implementation("${Dependencies.androidMaterial}:${Versions.androidMaterial}")
//
    implementation("${Dependencies.gson}:${Versions.gson}")
}