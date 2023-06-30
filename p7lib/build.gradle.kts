plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespaceP7Lib
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
    externalNativeBuild {
        cmake {
            path = File("src/main/cpp/CMakeLists.txt")
            version = "3.18.1"
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

    implementation(project(":p7libapi"))

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    implementation("${Dependencies.composeRuntime}:${Versions.compose}")

    //implementation 'androidx.core:core-ktx:1.7.20'
    //implementation 'androidx.appcompat:appcompat:1.6.1'
    //implementation 'com.google.android.material:material:1.8.0'
    //testImplementation 'junit:junit:4.13.2'
    //androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    //androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    //implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
}