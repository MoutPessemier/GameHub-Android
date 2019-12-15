package digitized.gamehub.cardStack

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class CardStackViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database)
    private val userRepository = UserRepository(database)

    val parties = partyRepository.parties
    val games = gameRepository.games


    init {
        coroutineScope.launch {
            getPartiesNearYou(1000, 51.0538286, 3.7250121, "5db8838eaffe445c66076a89")
            gameRepository.getGames()
        }
    }

    /**
     * Gets the parties for a user that are not yet declined or joined by him
     * and within the max distance given up by the user
     */
    private fun getPartiesNearYou(distance: Int, lat: Double, long: Double, userId: String) {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.getPartiesNearYou(distance, lat, long, userId)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }

    fun refreshPartiesNearYou() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.getPartiesNearYou(10, 50.0, 50.0, "5db8838eaffe445c66076a88")
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
    fun joinParty() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.joinParty(PartyInteractionDTO("", ""))
                _status.value = ApiStatus.DONE
            } catch (e: Exception){
                _status.value = ApiStatus.ERROR
                Timber.d(e)
            }
        }
    }

    /**
     * Decline a party
     */
    fun declineParty() {
        coroutineScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                partyRepository.declineParty(
                    PartyInteractionDTO(
                        "",
                        ""
                    )
                )
                _status.value = ApiStatus.DONE
            } catch (e: Exception){
                _status.value = ApiStatus.ERROR
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
