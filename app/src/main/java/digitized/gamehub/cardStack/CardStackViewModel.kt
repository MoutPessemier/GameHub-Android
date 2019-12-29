package digitized.gamehub.cardStack

import android.app.Application
import android.location.Location
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.User
import digitized.gamehub.network.DTO.LoginDTO
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDomainModel
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import java.lang.Exception
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CardStackViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository =
        PartyRepository(database, sharedPreferences.getString("userId", "")!!)
    private val userRepository = UserRepository(database)

    val parties = partyRepository.newParties
    val games = gameRepository.games
    val user = userRepository.user

    var usr: User? = null
    var currentLocation: Location? = null

    init {
        coroutineScope.launch {
            gameRepository.getGames()
            getUser()
        }
    }

    /**
     * Gets the parties for a user that are not yet declined or joined by him
     * and within the max distance given up by the user
     */
    fun getPartiesNearYou() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.getPartiesNearYou(
                    usr!!.maxDistance,
                    usr!!.latitude!!,
                    usr!!.longitude!!,
                    usr!!.id
                )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }

    /**
     * Join a party
     */
    fun joinParty(partyId: String, userId: String) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.joinParty(PartyInteractionDTO(partyId, userId))
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }

    /**
     * Decline a party
     */
    fun declineParty(partyId: String, userId: String) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.declineParty(
                    PartyInteractionDTO(
                        partyId,
                        userId
                    )
                )
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }

    private suspend fun getUser() {
        val email = sharedPreferences.getString("email", null)
        if (email != null) {
            withContext(Dispatchers.IO) {
                val user = GameHubAPI.service.login(LoginDTO(email)).await()
                userRepository.insertUser(user.asDomainModel())
            }
        }
    }

    fun updateUserLocation(latitude: Double?, longitude: Double?) {
        coroutineScope.launch {
            try {
                usr!!.latitude = latitude
                usr!!.longitude = longitude
                userRepository.updateAccount(usr!!)
            } catch (e: Exception) {
                Timber.d(e)
            }
        }
    }

    fun cleardb() {
        coroutineScope.launch {
            partyRepository.clearDb()
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CardStackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CardStackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
