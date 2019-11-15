package digitized.gamehub.map

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.auth0.android.result.UserIdentity
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = GameHubDatabase.getInstance(application)
    private val userRepository = UserRepository(database)
    private val partyRepository = PartyRepository(database)

    val parties = partyRepository.parties

    fun updateUserLocation(lat: Double?, long: Double?) {
        val user = userRepository.user!!.value!!
        coroutineScope.launch {
            userRepository.updateAccount(user, lat, long)
        }
    }




    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}