// Top-level build file where you can add configuration options common to all sub-projects/modules.
tasks.register("createPreCommitHook", Copy::class.java) {
    val scriptHookPath = "$rootDir/scripts/pre-commit"
    val hooksDir = "$rootDir/.git/hooks/"

    val gitHooksDirectory = File(hooksDir)
    if (!gitHooksDirectory.exists()) gitHooksDirectory.mkdirs()

    group = "git hooks"
    from(scriptHookPath)
    into(hooksDir)
    fileMode = 775

    doLast {
        val preCommitFile = File("$project.rootDir/.git/hooks/pre-commit")
        if (preCommitFile.setExecutable(true)) logger.quiet("Set pre-commit hook as executable.")
        else logger.warn("Failed to set pre-commit hook as executable.")
    }
}

afterEvaluate {
    tasks.getByPath(":app:assembleDebug").dependsOn(":createPreCommitHook")
}

plugins {
    id("com.android.application") version Versions.androidApplication apply false
    id("com.android.library") version Versions.androidApplication apply false
    kotlin("android") version Versions.kotlinAndroid apply false

    id("com.diffplug.spotless") version Versions.spotless
    id("io.gitlab.arturbosch.detekt") version Versions.detekt
}


allprojects {
    apply(from = "${project.rootDir}/detekt.gradle")
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
