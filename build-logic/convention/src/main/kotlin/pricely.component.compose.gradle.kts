import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.kotlin.dsl.configure
import pricely.addComposeDependencies

pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        buildFeatures.compose = true
    }
    addComposeDependencies()
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        buildFeatures.compose = true
    }
    addComposeDependencies()
}
