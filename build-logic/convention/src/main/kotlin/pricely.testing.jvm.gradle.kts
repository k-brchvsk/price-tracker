import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType
import pricely.libs

dependencies {
    val catalog = project.libs
    add("testImplementation", catalog.findLibrary("kotest-runner-junit5").get())
    add("testImplementation", catalog.findLibrary("kotest-assertions-core").get())
    add("testImplementation", catalog.findLibrary("mockk").get())
    add("testImplementation", catalog.findLibrary("turbine").get())
    add("testImplementation", catalog.findLibrary("kotlinx-coroutines-test").get())
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    jvmArgs("-Dkotlinx.coroutines.debug=off")
}
