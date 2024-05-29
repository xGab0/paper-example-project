pluginManagement {
    plugins {
        // add toolchain resolver
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
    }

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