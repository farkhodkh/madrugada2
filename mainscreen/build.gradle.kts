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

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":ui"))
    implementation(project(":sdkapi"))
    implementation(project(":p7libapi"))
    implementation(project(":p7lib"))
    implementation(project(":networkapi"))
    implementation(project(":room"))
    implementation(project(":persistence"))
    implementation(project(":printerApi"))
    implementation(project(mapOf("path" to ":p7libapi")))
}