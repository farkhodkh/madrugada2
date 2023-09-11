plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("ru.petrolplus.pos.convention")
    id("ru.petrolplus.pos.compose")
}

android {
    namespace = Dependencies.namespaceMainScreen

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

    //Lifecycle
    implementation("${Dependencies.lifecycleViewModel}:${Versions.lifecycle}")
    implementation("${Dependencies.lifecycleRuntime}:${Versions.lifecycle}")

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":presentation_ui"))
    implementation(project(":data_sdkapi"))
    implementation(project(":p7libapi"))
    implementation(project(":p7lib"))
    implementation(project(":data_networkapi"))
    implementation(project(":data_room"))
    implementation(project(":data_persistence"))
    implementation(project(":presentation_printerApi"))
}