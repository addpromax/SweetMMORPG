rootProject.name = "SweetMMORPG"

include(":mmoitems")
File("mmoitems").listFiles()?.forEach { file ->
    if (file.resolve("build.gradle.kts").exists()) {
        include(":mmoitems:${file.name}")
    }
}
