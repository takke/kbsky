import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version "1.9.22"
    kotlin("native.cocoapods") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
        macosX64(),
        macosArm64(),
    ).forEach {
        it.binaries.framework {
            xcf.add(this)
        }
    }

    cocoapods {
        version = "0.0.1"
        summary = "kbsky is Bluesky/ATProtocol library for Kotlin Multiplatform."
        homepage = "https://github.com/uakihir0/kbsky"
        authors = "Akihiro Urushihara"
        license = "MIT"
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":stream"))
        }
    }
}

tasks.podPublishXCFramework {
    doLast {
        exec {
            executable = "sh"
            args = listOf("./tool/rename_podfile.sh")
        }
    }
}
