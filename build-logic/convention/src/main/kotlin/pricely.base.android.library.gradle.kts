import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.configure
import pricely.androidNamespace
import pricely.versionInt
import pricely.versionString

plugins {
    id("com.android.library")
}

pluginManager.apply("pricely.config.detekt")

extensions.configure<LibraryExtension> {
    namespace = project.androidNamespace()
    compileSdk = project.versionInt("compileSdk")

    defaultConfig {
        minSdk = project.versionInt("minSdk")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            pickFirsts += "META-INF/LICENSE.md"
            pickFirsts += "META-INF/LICENSE-notice.md"
            pickFirsts += "META-INF/NOTICE.md"
        }
    }

    compileOptions {
        val javaVersion = JavaVersion.toVersion(project.versionString("java"))
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
}
