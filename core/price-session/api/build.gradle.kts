plugins {
    alias(libs.plugins.convention.pricely.base.kotlin.library)
    alias(libs.plugins.convention.pricely.testing.jvm)
}

dependencies {
    libs {
        implementation(kotlinx.coroutines.core)
    }
}
