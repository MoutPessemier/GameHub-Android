package digitized.gamehub.cardStack

import android.app.Application
import android.content.SharedPreferences
import android.location.Location
import android.preference.PreferenceManager
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.User
import digitized.gamehub.network.DTO.LoginDTO
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDomainModel
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception

class CardStackViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database, sharedPreferences.getString("userId", "")!!)
    private val userRepository = UserRepository(database)

    val parties = partyRepository.newParties
    val games = gameRepository.games
    val user = userRepository.user

    var usr: User? = null
    var currentParty: GameParty? = null
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
    // distance: Int, lat: Double, long: Double, userId: String
    fun getPartiesNearYou() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.getPartiesNearYou(usr!!.maxDistance, usr!!.latitude!!, usr!!.longitude!!, usr!!.id)
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
