import org.gradle.kotlin.dsl.dependencies
import pricely.addKoinAndroidStack
import pricely.libs

dependencies {
    add("implementation", project.libs.findLibrary("koin-core").get())
}

pluginManager.withPlugin("com.android.application") {
    addKoinAndroidStack()
}

pluginManager.withPlugin("com.android.library") {
    addKoinAndroidStack()
}
