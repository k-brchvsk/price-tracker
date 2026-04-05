plugins {
    alias(libs.plugins.convention.pricely.base.android.library)
    alias(libs.plugins.convention.pricely.component.compose)
}

dependencies {
    libs {
        implementation(androidx.core.ktx)
        implementation(coil.compose)
    }
}
