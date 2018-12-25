import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile

plugins {
    id("org.jetbrains.kotlin.jvm").version("1.3.11")
    id("org.jetbrains.kotlin.kapt").version("1.3.11")
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("net.octyl.apt-creator:apt-creator-annotations:0.1.1")
    kapt("net.octyl.apt-creator:apt-creator-processor:0.1.1")
}

tasks.named<KotlinJvmCompile>("compileKotlin") {
    kotlinOptions.freeCompilerArgs += "-XXLanguage:+InlineClasses"
}

val checkTestCase by tasks.registering {
    description = "Checks if the test case passes."
    dependsOn("kaptGenerateStubsKotlin")
    doLast {
        val content = project.buildDir.resolve("./tmp/kapt3/stubs/main/net/octyl/kiavi/FooProvider.java")
                .readLines().joinToString("\n")
        if ("""
            public abstract void setTheFoo(@org.jetbrains.annotations.NotNull()
    java.lang.String p0);
        """.trim() !in content) {
            throw IllegalStateException("No setter generated in stub, test case fails.")
        }
    }
}
