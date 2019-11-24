package digitized.gamehub.map

import android.app.Application
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
import java.lang.Exception

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = GameHubDatabase.getInstance(application)
    private val userRepository = UserRepository(database)
    private val partyRepository = PartyRepository(database)

    val parties = partyRepository.joinedParties

    fun updateUserLocation(lat: Double?, long: Double?) {
        coroutineScope.launch {
            try {
                val user = userRepository.user!!.value!!
                //userRepository.updateAccount(user, lat, long)
            } catch (e: Exception) {

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