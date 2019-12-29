package digitized.gamehub

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import timber.log.Timber

class GameHubApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
