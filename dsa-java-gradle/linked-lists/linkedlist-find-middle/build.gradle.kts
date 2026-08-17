// ---------------------------------------------------------------------------
// build.gradle.kts -- the "recipe" Gradle follows to compile, test and run
// this project. It is written in Kotlin (that is what the ".kts" means).
// Every block below is explained in docs/README.md, section "How Gradle works".
// ---------------------------------------------------------------------------

plugins {
    // Teaches Gradle how to compile Java and how to run JUnit tests.
    id("java")

    // Adds the `./gradlew run` task so we can execute Main.main() easily.
    id("application")
}

group = "org.jk.dsa.learning"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        // Java 21 (LTS). If your machine does not have a JDK 21, Gradle will
        // download one for you -- you do not have to install anything by hand.
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    // Which class has the `public static void main(String[] args)` we run.
    mainClass.set("org.jk.dsa.learning.Main")
}

repositories {
    // Where Gradle downloads third-party libraries (here: JUnit 5) from.
    mavenCentral()
}

// Lombok is an ANNOTATION PROCESSOR: it runs during compilation and writes the
// boilerplate (constructors, getters, toString, ...) into the .class files for
// you. Two consequences worth understanding before you use it:
//   1. `compileOnly` -- the Lombok jar is needed to COMPILE but not to RUN, so
//      it never ends up inside our jar. That is why it is not `implementation`.
//   2. `annotationProcessor` -- this is what actually switches the code
//      generation on. Declaring only `compileOnly` compiles but generates
//      NOTHING, which produces very confusing "cannot find symbol" errors.
// The same pair has to be repeated for the test source set.
val lombokVersion = "1.18.34"

dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // The JUnit "BOM" pins one consistent version for every junit artifact,
    // so we never have to repeat version numbers below.
    testImplementation(platform("org.junit:junit-bom:5.10.2"))

    // The API + engine we write tests against (@Test, assertEquals, ...).
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Required at runtime by Gradle 8+ to actually launch the JUnit platform.
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    // Without this line Gradle would look for old JUnit 4 tests and find none.
    useJUnitPlatform()

    // Print a readable line for every test so a student can see what ran.
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

// Convenience task: `./gradlew showGenerated` prints the members Lombok wrote
// into Node.class and Student.class. Seeing the generated methods with your own
// eyes is the fastest cure for "where did that constructor come from?".
tasks.register<Exec>("showGenerated") {
    group = "help"
    description = "Uses javap to show the methods Lombok generated."
    dependsOn(tasks.named("classes"))
    val classesDir = layout.buildDirectory.dir("classes/java/main").get().asFile
    commandLine("javap", "-p",
            "$classesDir/org/jk/dsa/learning/Node.class",
            "$classesDir/org/jk/dsa/learning/Main\$Student.class")
}

// Convenience task: `./gradlew demo` is a friendlier alias for `./gradlew run`.
tasks.register("demo") {
    group = "application"
    description = "Runs the console demo in Main.java (alias for 'run')."
    dependsOn(tasks.named("run"))
}
