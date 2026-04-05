import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.tasks.util.PatternSet
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import pricely.libs
import pricely.versionString

plugins {
    id("io.gitlab.arturbosch.detekt")
}

configure<DetektExtension> {
    config.from(
        rootProject.file("config/detekt/pricely.yml"),
        rootProject.file("config/detekt/compose.yml"),
    )
    autoCorrect = System.getProperty("DETEKT_AUTOCORRECT")?.toBooleanStrictOrNull() ?: true
    parallel = true
    allRules = false
    debug = false
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = versionString("java")
    setSource(
        fileTree(project.projectDir).matching(
            PatternSet().apply {
                include("src/main/**/*.kt")
            },
        ),
    )
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = versionString("java")
    setSource(
        fileTree(project.projectDir).matching(
            PatternSet().apply {
                include("src/main/**/*.kt")
            },
        ),
    )
}

dependencies {
    add("detektPlugins", libs.findLibrary("detekt-formatting").get())
    add("detektPlugins", libs.findLibrary("detekt-composePluginLopez").get())
    add("detektPlugins", libs.findLibrary("detekt-composePluginKode").get())
}
