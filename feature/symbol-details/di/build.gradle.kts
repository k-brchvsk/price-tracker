plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.component.koin)
}

dependencies {
    projects {
        implementation(core.priceSession.api)
        implementation(feature.symbolDetails.api)
        implementation(feature.symbolDetails.internal)
        implementation(navigation.api)
    }
}
