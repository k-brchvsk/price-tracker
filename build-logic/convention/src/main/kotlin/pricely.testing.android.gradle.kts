import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import pricely.libs

dependencies {
    val catalog = project.libs
    add("testImplementation", catalog.findLibrary("kotest-runner-junit5").get())
    add("testImplementation", catalog.findLibrary("kotest-assertions-core").get())
    add("testImplementation", catalog.findLibrary("mockk").get())
    add("testImplementation", catalog.findLibrary("turbine").get())
    add("testImplementation", catalog.findLibrary("kotlinx-coroutines-test").get())
    add("androidTestImplementation", catalog.findLibrary("kotest-assertions-core").get())
    add("androidTestImplementation", catalog.findLibrary("mockk-android").get())
    add("androidTestImplementation", catalog.findLibrary("turbine").get())
    add("androidTestImplementation", catalog.findLibrary("kotlinx-coroutines-test").get())
    add("androidTestImplementation", catalog.findLibrary("androidx-test-core").get())
    add("androidTestImplementation", catalog.findLibrary("androidx-test-ext-junit").get())
    add("androidTestImplementation", catalog.findLibrary("androidx-lifecycle-runtime-testing").get())
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    jvmArgs("-Dkotlinx.coroutines.debug=off")
}

pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        @Suppress("DEPRECATION")
        testOptions.unitTests.isReturnDefaultValues = true
        testOptions.unitTests.all { test ->
            test.useJUnitPlatform()
        }
    }
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        @Suppress("DEPRECATION")
        testOptions.unitTests.isReturnDefaultValues = true
        testOptions.unitTests.all { test ->
            test.useJUnitPlatform()
        }
    }
}
