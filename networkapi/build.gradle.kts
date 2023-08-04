plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespaceNetworkApi
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
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
dependencies {

    //implementation(project(":p7libapi"))

    //Dagger 2
    //implementation("${Dependencies.dagger}:${Versions.dagger}")
    implementation("${Dependencies.composeRuntime}:${Versions.compose}")

    //Okhttp
    implementation ("com.localebro:okhttpprofiler:1.0.8")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation ("com.localebro:okhttpprofiler:1.0.8")
    implementation ("com.google.android.gms:play-services-auth:20.6.0")

    //implementation 'androidx.core:core-ktx:1.7.20'
    //implementation 'androidx.appcompat:appcompat:1.6.1'
    //implementation 'com.google.android.material:material:1.8.0'
    //testImplementation 'junit:junit:4.13.2'
    //androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    //androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    //implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'

    //Modules
    //implementation(project(":core"))
    implementation(project(":network"))
}