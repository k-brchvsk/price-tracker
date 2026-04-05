package dev.kigya.pricely

import android.app.Application
import dev.kigya.pricely.di.appModules
import dev.kigya.pricely.di.installSingletonImageLoaderFromKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(appModules)
        }

        installSingletonImageLoaderFromKoin()
    }
}
