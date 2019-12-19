package digitized.gamehub.map

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.result.UserIdentity
import digitized.gamehub.createParty.CreatePartyViewModel
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    private val database = GameHubDatabase.getInstance(application)
    private val userRepository = UserRepository(database)
    private val partyRepository = PartyRepository(database, sharedPreferences.getString("userId", "")!!)

    val parties = partyRepository.joinedParties

    /**
     * Writes the new location to the database
     */
    fun updateUserLocation(latitude: Double?, longitude: Double?) {
        coroutineScope.launch {
            try {
                var user = userRepository.user.value!!
                user.latitude = latitude
                user.longitude = longitude
                userRepository.updateAccount(user)
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}