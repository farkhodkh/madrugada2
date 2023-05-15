plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespaceMainscreen
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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
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

    //Compose
    implementation("${Dependencies.composeConstraint}:${Versions.composeConstraint}")
    implementation("${Dependencies.composeUi}:${Versions.compose}")
    implementation("${Dependencies.composeMaterial}:${Versions.compose}")
    implementation("${Dependencies.composeUiUtil}:${Versions.compose}")
    implementation("${Dependencies.composeUiToolPreview}:${Versions.compose}")
    implementation("${Dependencies.composeUiTool}:${Versions.compose}")
    implementation("${Dependencies.activityCompose}:${Versions.activityCompose}")
    implementation("${Dependencies.composeNavigation}:${Versions.composeNavigation}")
    implementation("${Dependencies.composeFoundation}:${Versions.compose}")


    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":ui"))
}