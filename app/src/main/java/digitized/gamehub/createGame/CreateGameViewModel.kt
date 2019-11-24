package digitized.gamehub.createGame

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.repositories.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CreateGameViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var game: Game? = null
        private set

    private val database = getInstance(application)
    private val gameRepository= GameRepository(database)

//    fun createGame(
//        g: Game
//    ) {
//        coroutineScope.launch {
//            val createdGame =
//                GameHubAPI.service.createGame(g)
//            try {
//                _status.value = ApiStatus.LOADING
//                val result = createdGame.await()
//                _status.value = ApiStatus.DONE
//                // game = result
//            } catch (e: Exception) {
//                _status.value = ApiStatus.ERROR
//                game = null
//            }
//        }
//    }

//    fun updateGame(
//        id: String,
//        name: String,
//        description: String,
//        rules: String,
//        requirements: String,
//        type: GameType
//    ) {
//        coroutineScope.launch {
//            val updatedGame =
//                GameHubAPI.service.updateGame(Game(id, name, description, rules, requirements, type, true))
//            try {
//                _status.value = ApiStatus.LOADING
//                val result = updatedGame.await()
//                _status.value = ApiStatus.DONE
//                // game = result
//            } catch (e: Exception) {
//                _status.value = ApiStatus.ERROR
//                game = null
//            }
//        }
//    }

//    fun deleteGame(id: String) {
//        coroutineScope.launch {
//            val deletedGame = GameHubAPI.service.deleteGame(id)
//            try {
//                _status.value = ApiStatus.LOADING
//                val result = deletedGame.await()
//                _status.value = ApiStatus.DONE
//            } catch (e: Exception) {
//                _status.value = ApiStatus.ERROR
//            }
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun convertToType(spinnerType: String): GameType {
        return when (spinnerType) {
            "Card Game" -> GameType.CARD_GAME
            "Video Game" -> GameType.VIDEO_GAME
            "DnD" -> GameType.DnD
            "Party Game" -> GameType.PARTY_GAME
            "Board Game" -> GameType.BOARD_GAME
            "Family Game" -> GameType.FAMILY_GAME
            else -> GameType.UNKNOWN
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateGameViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateGameViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
