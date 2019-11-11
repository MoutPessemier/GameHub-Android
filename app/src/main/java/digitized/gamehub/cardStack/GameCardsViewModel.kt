package digitized.gamehub.cardStack

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.PartyInteractionDTO
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameCardsViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var party: GameParty? = null

    private var database = getInstance(application)
    private var gameRepository = GameRepository(database)
    private var partyRepository = PartyRepository(database)


    fun joinParty(partyId: String, userId: String) {
        coroutineScope.launch {
            val partyToJoin = GameHubAPI.service.joinParty(PartyInteractionDTO(partyId, userId))
            try {
                _status.value = ApiStatus.LOADING
                val result = partyToJoin.await()
                _status.value = ApiStatus.DONE
                //party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                party = null
            }
        }
    }

    fun declineParty(partyId: String, userId: String) {
        coroutineScope.launch {
            val partyToDecline = GameHubAPI.service.declineParty(PartyInteractionDTO(partyId, userId))
            try {
                _status.value = ApiStatus.LOADING
                val result = partyToDecline.await()
                _status.value = ApiStatus.DONE
                //party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                party = null

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GameCardsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GameCardsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}