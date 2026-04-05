import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import pricely.androidNamespace
import pricely.versionInt
import pricely.versionString

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    namespace = project.androidNamespace()
    compileSdk = project.versionInt("compileSdk")

    defaultConfig {
        minSdk = project.versionInt("minSdk")
        targetSdk = project.versionInt("targetSdk")
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        val javaVersion = JavaVersion.toVersion(project.versionString("java"))
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
