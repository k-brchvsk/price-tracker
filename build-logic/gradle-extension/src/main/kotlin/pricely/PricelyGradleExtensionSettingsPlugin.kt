package pricely

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings

class PricelyGradleExtensionSettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings) = Unit
}
