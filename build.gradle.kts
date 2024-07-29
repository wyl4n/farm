plugins {
    kotlin("jvm") version "1.9.22"

    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"

    id ("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.redepotter.souls"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven ("https://jitpack.io")

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly ("com.github.azbh111:craftbukkit-1.8.8:R")
    implementation("com.github.SaiintBrisson.command-framework:bukkit:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    compileOnly ("com.github.MilkBowl:VaultAPI:1.7")

    implementation(fileTree("inventory"))

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")
}

bukkit {
    author = "wylan"
    main = "com.redepotter.souls.farm.FarmPlugin"
    version = "0.0.1"
}


tasks.shadowJar {
    archiveFileName.set("farm.jar")
}
kotlin {
    jvmToolchain(8)
}
