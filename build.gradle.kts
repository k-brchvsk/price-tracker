plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.detekt) apply false
}

tasks.register("detekt") {
    group = "verification"
    description = "Runs detekt on all subprojects that apply the Pricely detekt convention"
}

subprojects {
    afterEvaluate {
        if (pluginManager.hasPlugin("io.gitlab.arturbosch.detekt")) {
            rootProject.tasks.named("detekt").configure { dependsOn(tasks.named("detekt")) }
        }
    }
}
