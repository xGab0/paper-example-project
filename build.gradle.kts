import xyz.jpenilla.runtask.task.AbstractRun

plugins {
    id("java")

    // https://github.com/johnrengelman/shadow/releases/tag/8.1.1
    id("com.github.johnrengelman.shadow") version "8.1.1"

    // https://docs.papermc.io/paper/dev/userdev
    // https://plugins.gradle.org/plugin/io.papermc.paperweight.userdev
    id("io.papermc.paperweight.userdev") version "1.7.1"

    // https://github.com/jpenilla/run-task
    // Adds runServer and runDevBundleServer tasks for testing
    id("xyz.jpenilla.run-paper") version "2.3.0"

    // https://github.com/jpenilla/resource-factory
    // Generates plugin.yml based on the Gradle config
    id("xyz.jpenilla.resource-factory-paper-convention") version "1.1.1"

    id("maven-publish")
}

base {
    version = findProperty("plugin_version")!!
    group = findProperty("maven_group")!!
    archivesName = "paper-example-project"
}

repositories {
    maven {
        name = "PaperMC"
        url  = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "Fabric"
        url  = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    val minecraft_version = findProperty("minecraft_version")
    val paper_version = findProperty("paper_version")

    // Must be kept in sync with upstream!
    // replaced tiny-remapper with the newer mapping-io from FabricMC
    // https://github.com/FabricMC/mapping-io
    //
    remapper("net.fabricmc:tiny-remapper:0.10.2:fat")
    //remapper("net.fabricmc:mapping-io:0.6.1")

    // Must be kept in sync with upstream
    // since paperweight 1.7 vineflower is now the default decompiler
    // this is just an example to show this function
    //decompiler("org.vineflower:vineflower:1.10.1")

    paperweight.paperDevBundle("$paper_version")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21

    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }

    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Javadoc> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Jar> {
        from("LICENSE")
    }

    // make build reproducible
    tasks.withType<AbstractArchiveTask> {
        isPreserveFileTimestamps = false
        isReproducibleFileOrder = true
    }

    tasks.withType<ProcessResources> {
        filteringCharset = "UTF-8"

        // Use the entire following code only if you will not generate the file using
        // the generator but will use the existing file in the resource folder
        /*
        val minecraft_version = findProperty("minecraft_version")
        val paper_version = findProperty("paper_version")

        // exclude fabric.mod.json in resources if you are
        // generating it with the resource-factory plugin
        exclude("paper-plugin.yml")

        inputs.property("version", project.version)
        inputs.property("minecraft_version", minecraft_version)
        inputs.property("paper_version", paper_version)

        filesMatching("paper-plugin.yml") {
            expand(mapOf(
                "version" to project.version,
                "minecraft_version" to minecraft_version,
                "paper_version" to paper_version
            ))
        }
        */
    }
}

// https://docs.papermc.io/paper/dev/getting-started/paper-plugins#how-do-i-use-them
// Defaults for name, version, and description are inherited from the Gradle project
paperPluginYaml {
    main = "com.example.exampleplugin.ExamplePlugin"
    bootstrapper = "com.example.exampleplugin.ExamplePluginBootstrap"
    loader = "com.example.exampleplugin.ExamplePluginLoader"
    apiVersion = "1.20.5"
    //name = "ExamplePlugin"
    //version = "0.1-SNAPSHOT"
    //description = "My paper plugin example"
    authors.add("MyName")
}

// Task for the hot-swap
// https://github.com/jpenilla/run-task/wiki/Debugging#hot-swap
tasks.withType<AbstractRun> {
    javaLauncher = javaToolchains.launcherFor {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(21)
    }

    jvmArgs(
        //"-XX:+IgnoreUnrecognizedVMOptions",
        //"-XX:+AllowEnhancedClassRedefinition",
        //"-XX:+AllowRedefinitionToAddDeleteMethods"
    )
}

// configure the maven publication
// https://docs.gradle.org/current/userguide/publishing_maven.html
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.example.exampleplugin"
            artifactId = project.name
            version = "${project.version}"

            from(components["java"])

            //artifact(tasks.remapJar)
            //artifact(tasks.remapSourcesJar)
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.

        // uncomment to publish to the local maven
        // mavenLocal()
    }
}

tasks {
    // https://github.com/jpenilla/run-task/wiki/Basic-Usage
    runDevBundleServer {
        downloadPlugins {
            url("https://download.luckperms.net/1543/bukkit/loader/LuckPerms-Bukkit-5.4.130.jar")
        }
    }

    // https://github.com/jpenilla/run-task/wiki/Basic-Usage
    runServer {
        downloadPlugins {
            url("https://download.luckperms.net/1543/bukkit/loader/LuckPerms-Bukkit-5.4.130.jar")
        }
    }
}