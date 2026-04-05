plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.component.compose)
    alias(libs.plugins.convention.pricely.component.koin)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    projects {
        implementation(core.designSystem)
        implementation(core.priceSession.api)
        implementation(feature.symbolDetails.api)
        implementation(navigation.api)
    }

    libs {
        implementation(androidx.core.ktx)
        implementation(androidx.lifecycle.runtime.ktx)
        implementation(androidx.lifecycle.viewmodel.ktx)
        implementation(androidx.navigation.compose)
        implementation(kotlinx.serialization.json)
    }
}
