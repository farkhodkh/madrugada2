// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.androidApplication apply false
    id("com.android.library") version Versions.androidApplication apply false
    kotlin("android") version Versions.kotlinAndroid apply false

    id("com.diffplug.spotless") version Versions.spotless
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        ratchetFrom("origin/develop")

        java {
            target("**/*.java")
            googleJavaFormat().aosp()
            removeUnusedImports()
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        kotlin {
            target("**/*.kt")
            ktlint(Versions.ktlint)
                .userData(mapOf(
                    "android" to "true",
                    "ignoreFailure" to "false",
                    "disabled_rules" to "final-newline"
                ))
                .setEditorConfigPath("${project.rootDir}/.editconfig")
            trimTrailingWhitespace()
            indentWithSpaces()
            endWithNewline()
        }

        format("misc") {
            target("**/*.gradle", "**/*.md", "**/.gitignore")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }

        format("xml") {
            target("**/*.xml")
            indentWithSpaces()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
}
