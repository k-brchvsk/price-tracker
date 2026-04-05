plugins {
    alias(libs.plugins.convention.pricely.base.kotlin.library)
}

dependencies {
    libs {
        implementation(kotlinx.coroutines.core)
    }
}
