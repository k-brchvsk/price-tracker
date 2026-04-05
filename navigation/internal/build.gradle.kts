plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
}

dependencies {
    projects {
        implementation(navigation.api)
    }

    libs {
        implementation(androidx.navigation.compose)
    }
}
