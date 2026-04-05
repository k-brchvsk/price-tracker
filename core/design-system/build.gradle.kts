plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.component.compose)
    alias(libs.plugins.convention.pricely.testing.android)
}

dependencies {
    libs {
        implementation(androidx.core.ktx)
        implementation(coil.compose)
    }
}
