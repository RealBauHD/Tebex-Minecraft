import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    id("com.gradleup.shadow") version "8.3.1"
}

defaultTasks("shadowJar")

group = "io.tebex"
version = "2.1.0"

subprojects {
    plugins.apply("java")
    plugins.apply("com.gradleup.shadow")
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(8))
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.named("shadowJar", ShadowJar::class.java) {
        archiveFileName.set("tebex-${project.name}-${rootProject.version}.jar")
    }

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") {
            name = "spigotmc-repo"
        }
        maven("https://oss.sonatype.org/content/groups/public/") {
            name = "sonatype"
        }
        maven("https://repo.opencollab.dev/main/") {
            name = "opencollab-snapshot-repo"
        }
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "paper-repo"
        }
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/") {
            name = "extendedclip-repo"
        }
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            name = "sonatype-snapshots"
        }
        maven("https://maven.nucleoid.xyz/") {
            name = "nucleoid"
        }
    }

    tasks.named("processResources", Copy::class.java) {
        val props = mapOf("version" to rootProject.version, "@VERSION@" to rootProject.version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        filesNotMatching("**/*.zip") {
            expand(props)
        }
    }
}

val fabric1204Project = project(":fabric-1.20.4")
fabric1204Project.configure<JavaPluginExtension> {
    sourceSets {
        getByName("main") {
            java {
                srcDir("src/main/kotlin")
            }
        }
    }
}

val fabric1201Project = project(":fabric-1.20.1")
fabric1201Project.configure<JavaPluginExtension> {
    sourceSets {
        getByName("main") {
            java {
                srcDir("src/main/kotlin")
            }
        }
    }
}

val fabric1211Project = project(":fabric-1.21.1")
fabric1211Project.configure<JavaPluginExtension> {
    sourceSets {
        getByName("main") {
            java {
                srcDir("src/main/kotlin")
            }
        }
    }
}