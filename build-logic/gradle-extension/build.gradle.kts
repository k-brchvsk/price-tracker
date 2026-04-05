plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "dev.kigya.pricely.buildlogic"
version = "1.0"

repositories {
    gradlePluginPortal()
}

gradlePlugin {
    plugins {
        register("pricelyGradleExtensionSettings") {
            id = "pricely.gradle-extension-settings"
            implementationClass = "pricely.PricelyGradleExtensionSettingsPlugin"
        }
    }
}
