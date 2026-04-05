package pricely

import org.gradle.api.initialization.Settings

fun Settings.includeAll(vararg projectPaths: String) {
    projectPaths.forEach { include(it) }
}
