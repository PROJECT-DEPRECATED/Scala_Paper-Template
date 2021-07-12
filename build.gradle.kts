plugins {
    scala
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = properties["pluginGroup"]!!
version = properties["pluginVersion"]!!

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")

    jcenter()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.5")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks {
    javadoc {
        options.encoding = "UTF-8"
    }

    compileScala {
        scalaCompileOptions.isOptimize = true
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        sourceSets["main"].allSource
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

publishing {
    publications {
        create<MavenPublication>(project.name) {
            artifact(tasks["sourceJar"])
            from(components["java"])
        }
    }
}
