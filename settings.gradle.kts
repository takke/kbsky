import java.util.Locale

pluginManagement {
    includeBuild("plugins")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "kbsky"

include("core")
include("stream")
include("auth")

// exclude "all" on Windows OS
val osName = System.getProperty("os.name").lowercase(Locale.getDefault())
if (osName.contains("mac")) {
    include("all")
}
