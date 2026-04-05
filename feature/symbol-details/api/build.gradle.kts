plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.component.compose)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    projects {
        implementation(core.priceSession.api)
        implementation(navigation.api)
    }

    libs {
        implementation(androidx.navigation.compose)
        implementation(kotlinx.serialization.json)
    }
}
