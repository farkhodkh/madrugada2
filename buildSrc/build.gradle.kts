plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.3.1")
}

gradlePlugin {
    plugins {
        register("convenience_plugin") {
            id = "ru.petroplus.pos.convention"
            implementationClass = "ConventionPlugin"
        }

        register("compose_plugin") {
            id = "ru.petroplus.pos.compose"
            implementationClass = "ComposePlugin"
        }
    }
}