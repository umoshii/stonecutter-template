import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("net.fabricmc.fabric-loom")
	id("org.jetbrains.kotlin.jvm") version "2.4.10"

	`maven-publish`
}

version = "${property("mod.version")}+${sc.current.version}"
group = property("mod.group") as String
base.archivesName = property("mod.id") as String

repositories {
	// Add repositories to retrieve artifacts from in here.
	maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
	// minecraft version
	minecraft("com.mojang:minecraft:${sc.current.version}")

	// independent deps
	implementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    implementation("net.fabricmc:fabric-language-kotlin:${property("deps.fabric_language_kotlin")}")

	// dev-auth
	runtimeOnly("me.djtheredstoner:DevAuth-fabric:${property("deps.devauth")}")

	// dependent deps
	implementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")
}

tasks.processResources {
	// maps provided variables to .json templates to put into fabric.mod.json

	fun MutableMap<String, String>.register(key: String, property: String) {
		val value: String = sc.properties[property]
		inputs.property(key, value)
		set(key, value)
	}

	// specify vars to pass into fabric.mod.json here
	val props = buildMap {
		// mod metadata
		register("mod_id", "mod.id")
		register("mod_name", "mod.name")
		register("mod_version", "mod.version")
		register("mod_mc_compat", "mod.mc_compat")
		register("mod_desc", "mod.desc")

		// deps
		// ...
	}

	filesMatching("fabric.mod.json") { expand(props) }
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

kotlin {
	compilerOptions {
		jvmTarget = JvmTarget.JVM_25
	}
}

java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
	val projectName = project.name
	inputs.property("projectName", projectName)

	from("LICENSE") {
		rename { "${it}_$projectName" }
	}
}

publishing {
	// configure the maven publication

	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories { }
}
