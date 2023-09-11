plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = Dependencies.namespaceEvatorPrinter
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
    // Core
    implementation("${Dependencies.coreKtx}:${Versions.coreKtx}")
    implementation("${Dependencies.appcompat}:${Versions.appcompat}")

    //Coroutines
    implementation("${Dependencies.kotlinxCoroutines}:${Versions.kotlinxCoroutines}")

    // Evator
    implementation("${Dependencies.evotor}:${Versions.evotor}")
    implementation(project(":presentation_printerApi"))
    implementation(project(":data_persistence"))

    // Modules
    implementation(project(":util"))
    implementation(project(":evotorlib"))
}