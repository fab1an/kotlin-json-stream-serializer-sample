plugins {
    kotlin("jvm").version("2.1.20")
    id("com.google.devtools.ksp") version("2.1.20-1.0.31")
}

group = "com.fab1an"
version = "2.0.4"

repositories {
    mavenCentral()
}

dependencies {
    ksp("com.fab1an:kotlin-json-stream-serializer:2.0.4")
    implementation("com.fab1an:kotlin-json-stream-serializer-annotations:2.0.1")
    implementation("com.fab1an:kotlin-json-stream:1.2.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
