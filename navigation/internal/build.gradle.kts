plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.testing.android)
}

dependencies {
    projects {
        implementation(navigation.api)
        testImplementation(feature.feed.api)
        testImplementation(feature.symbolDetails.api)
    }

    libs {
        implementation(androidx.navigation.compose)
    }
}
