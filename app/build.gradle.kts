plugins {
    alias(libs.plugins.convention.pricely.base.android.application)
    alias(libs.plugins.convention.pricely.component.compose)
    alias(libs.plugins.convention.pricely.component.koin)
    alias(libs.plugins.convention.pricely.testing.android)
    alias(libs.plugins.kotlin.serialization)
}

android {
    defaultConfig {
        applicationId = "dev.kigya.pricely"

        manifestPlaceholders["deepLinkScheme"] = "stocks"
    }
}

dependencies {
    projects {
        implementation(core.designSystem)
        implementation(core.priceSession.internal)
        testImplementation(core.priceSession.api)
        implementation(feature.feed.api)
        implementation(feature.feed.di)
        implementation(feature.symbolDetails.api)
        implementation(feature.symbolDetails.di)
        implementation(navigation.api)
        implementation(navigation.internal)
    }

    libs {
        implementation(androidx.core.ktx)
        implementation(androidx.lifecycle.runtime.ktx)
        implementation(androidx.lifecycle.viewmodel.ktx)
        implementation(androidx.navigation.compose)
        implementation(kotlinx.serialization.json)
        implementation(coil.compose)
        implementation(coil.network.okhttp)
        implementation(coil.svg)
    }
}
