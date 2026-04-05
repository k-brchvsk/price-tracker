plugins {
    alias(libs.plugins.convention.pricely.base.kotlin.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.convention.pricely.component.koin)
}

dependencies {
    projects {
        implementation(core.priceSession.api)
    }

    libs {
        implementation(kotlinx.coroutines.core)
        implementation(kotlinx.serialization.json)
        implementation(okhttp)
    }
}
