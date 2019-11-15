package digitized.gamehub.createParty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.GameType
import digitized.gamehub.network.GameHubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CreatePartyViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>>
        get() = _games

    var party: GameParty? = null
        private set

    init {
        _games.value = listOf(Game("1","Temp", "jqdsf", "dqsf", "ezrez", GameType.CARD_GAME))
        //getGames()
    }



    private fun getGames() {
        coroutineScope.launch {
            val games = GameHubAPI.service.getAllGames()
            Timber.d(games.toString())
            try {
                _status.value = ApiStatus.LOADING
                val resultList = games.await()
                _status.value = ApiStatus.DONE
                //_games.value = resultList
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _games.value = ArrayList()
            }
        }
    }

    fun createParty(
        name: String,
        date: Date,
        maxSize: Int,
        participants: Array<String>,
        coordinates: DoubleArray,
        game: String
    ) {
        coroutineScope.launch {
            val createdGame =
                GameHubAPI.service.createParty(GameParty(null, name, date, maxSize, participants, game))
            try {
                _status.value = ApiStatus.LOADING
                val result = createdGame.await()
                _status.value = ApiStatus.DONE
                // party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                party = null
            }
        }
    }

    fun updateParty(
        id: String,
        name: String,
        date: Date,
        maxSize: Int,
        participants: Array<String>,
        coordinates: DoubleArray,
        gameId: String
    ) {
        coroutineScope.launch {
            val updatedParty = GameHubAPI.service.updateParty(
                GameParty(id, name, date, maxSize, participants, gameId)
            )
            try {
                _status.value = ApiStatus.LOADING
                val result = updatedParty.await()
                _status.value = ApiStatus.DONE
                // party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                party = null
            }
        }
    }

    fun deleteParty(id: String){
        coroutineScope.launch {
            val deletedParty = GameHubAPI.service.deleteParty(id)
            try {
                _status.value = ApiStatus.LOADING
                val result = deletedParty.await()
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

