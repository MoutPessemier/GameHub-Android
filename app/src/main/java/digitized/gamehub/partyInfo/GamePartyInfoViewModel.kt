package digitized.gamehub.partyInfo

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.GameType
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.PartyInteractionDTO
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class GamePartyInfoViewModel(var party: GameParty, application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _selectedParty = MutableLiveData<GameParty>()
    val selectedParty: LiveData<GameParty>
        get() = _selectedParty

    private val _partyGame = MutableLiveData<Game>()
    val partyGame: LiveData<Game>
        get() = _partyGame

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database)


    init {
        _selectedParty.value = party
        _partyGame.value = Game("1", "Temp", "description", "rules", "requirements", GameType.CARD_GAME)
    }

    private fun getGame(id: String) {
        coroutineScope.launch {
            val game = GameHubAPI.service.getGameById(id)
            try {
                _status.value = ApiStatus.LOADING
                val result = game.await()
                _status.value = ApiStatus.DONE
                // _partyGame.value = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _partyGame.value = null
            }
        }
    }

    fun joinParty(partyId: String, userId: String) {
        coroutineScope.launch {
            val partyToJoin = GameHubAPI.service.joinParty(PartyInteractionDTO(partyId, userId))
            try {
                _status.value = ApiStatus.LOADING
                val result = partyToJoin.await()
                _status.value = ApiStatus.DONE
                // party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val party: GameParty, val application: Application): ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GamePartyInfoViewModel::class.java)) {
                return GamePartyInfoViewModel(party, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}