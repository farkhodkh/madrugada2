
plugins {
    id(Dependencies.androidApplication) version Versions.androidApplication
    kotlin(Dependencies.kotlinAndroid) version Versions.kotlinAndroid
    id("kotlin-kapt")
}

android {
    namespace = Dependencies.namespace
    compileSdkVersion = Versions.compileSdkVersion

    defaultConfig {
        applicationId = Dependencies.applicationId
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = Dependencies.testInstrumentationRunner
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
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

    //SSL Authentification
    implementation ("com.google.android.gms:play-services-auth:20.6.0")
    implementation ("org.bouncycastle:bcprov-jdk15on:1.67")

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
    implementation("${Dependencies.composeRuntime}:${Versions.compose}")
    implementation("${Dependencies.activityCompose}:${Versions.activityCompose}")
    implementation("${Dependencies.composeNavigation}:${Versions.composeNavigation}")
    implementation("${Dependencies.composeFoundation}:${Versions.compose}")

    //Lifecycle
    //Lifecycle
    implementation("${Dependencies.lifecycleViewModel}:${Versions.lifecycle}")
    implementation("${Dependencies.lifecycleRuntime}:${Versions.lifecycle}")

    //Dagger 2
    implementation("${Dependencies.dagger}:${Versions.dagger}")
    kapt("${Dependencies.daggerCompiler}:${Versions.dagger}")

    //Room
    implementation("${Dependencies.roomKtx}:${Versions.room}")

    //Okhttp
    implementation("${Dependencies.gson}:${Versions.gson}")
    implementation("${Dependencies.okhttpProfiler}:${Versions.okhttpProfiler}")
    implementation("${Dependencies.okhttLoggingInterceptor}:${Versions.okhttLoggingInterceptor}")

    //Retrofit
    implementation("${Dependencies.retrofit}:${Versions.retrofit}")
    implementation("${Dependencies.gsonConverter}:${Versions.gsonConverter}")

    //Testing
    implementation("${Dependencies.junit}:${Versions.junit}")
    implementation("${Dependencies.extJunit}:${Versions.extJunit}")

    //Modules
    implementation(project(":core"))
    implementation(project(":util"))
    implementation(project(":ui"))
    implementation(project(":mainscreen"))
    implementation(project(":p7lib"))
    implementation(project(":p7libapi"))
    implementation(project(":evotorsdk"))
    implementation(project(":sdkapi"))
    implementation(project(":room"))
    implementation(project(":network"))
    implementation(project(":networkapi"))


}