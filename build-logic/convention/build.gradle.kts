plugins {
    `kotlin-dsl`
}

group = "dev.kigya.pricely.buildlogic"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation(gradleApi())
    libs {
        implementation(gradle.android)
        implementation(gradle.kotlin)
        implementation(gradle.detekt)
    }
}
