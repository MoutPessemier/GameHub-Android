package digitized.gamehub

import android.app.Application
import timber.log.Timber

class GameHubApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }
}