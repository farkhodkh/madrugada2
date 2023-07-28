plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

//android {
//    namespace = Dependencies.namespaceSdkApi
//    compileSdkVersion = Versions.compileSdkVersion
//
//    defaultConfig {
//        minSdk = Versions.minSdkVersion
//        targetSdk = Versions.targetSdk
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8.toString()
//    }
//
//    buildFeatures {
//        compose = true
//    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
//    }
//}
//
//dependencies {
//    implementation("${Dependencies.composeRuntime}:${Versions.compose}")
//}