pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven {
            name = "PaperMC"
            url  = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "Fabric"
            url  = uri("https://maven.fabricmc.net/")
        }
    }
}