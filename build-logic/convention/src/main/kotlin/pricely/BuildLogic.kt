package pricely

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.versionInt(name: String): Int = libs.findVersion(name).get().requiredVersion.toInt()

internal fun Project.versionString(name: String): String = libs.findVersion(name).get().requiredVersion

/**
 * Android `namespace` derived from the Gradle project path.
 *
 * 1. Take [Project.path] (e.g. `:feature:symbol-details:di`).
 * 2. Strip the leading `:`, split on `:` into path segments.
 * 3. Drop empty segments.
 * 4. In each segment, remove `-` (hyphens are not valid in package segments).
 * 5. If the only segment is `app`, use the root namespace (no `.app` suffix).
 * 6. Result: `dev.kigya.pricely` plus, when non-empty, `.` and segments joined by `.`.
 *
 * Examples: `:app` → `dev.kigya.pricely`; `:core:design-system` → `dev.kigya.pricely.core.designsystem`;
 * `:feature:symbol-details:di` → `dev.kigya.pricely.feature.symboldetails.di`.
 */
internal fun Project.androidNamespace(): String {
    val segments = path
        .removePrefix(":")
        .split(":")
        .filter { it.isNotBlank() }
        .map { it.replace("-", "") }
    val effective = if (segments == listOf("app")) {
        emptyList()
    } else {
        segments
    }
    return if (effective.isEmpty()) {
        "dev.kigya.pricely"
    } else {
        "dev.kigya.pricely.${effective.joinToString(".")}"
    }
}

internal fun Project.addComposeDependencies() {
    val composeBom: Provider<MinimalExternalModuleDependency> = libs.findLibrary("androidx-compose-bom").get()
    dependencies {
        add("implementation", platform(composeBom))
        add("implementation", libs.findLibrary("androidx-activity-compose").get())
        add("implementation", libs.findLibrary("androidx-compose-ui").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-graphics").get())
        add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        add("implementation", libs.findLibrary("androidx-compose-material3").get())
        add("implementation", libs.findLibrary("androidx-compose-material-icons-core").get())
        add("implementation", libs.findLibrary("androidx-compose-material-icons-extended").get())
        add("implementation", libs.findLibrary("androidx-lifecycle-runtime-compose").get())
        add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-tooling").get())
        add("debugImplementation", libs.findLibrary("androidx-compose-ui-test-manifest").get())
        add("androidTestImplementation", platform(composeBom))
        add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test-junit4").get())
    }
}

internal fun Project.addKoinAndroidStack() {
    dependencies {
        add("implementation", libs.findLibrary("koin-android").get())
        add("implementation", libs.findLibrary("koin-androidx-compose").get())
    }
}
