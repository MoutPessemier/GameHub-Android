package digitized.gamehub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType
import digitized.gamehub.network.GameHubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateGameViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var game: Game? = null
        private set

    fun createGame(
        name: String,
        description: String,
        rules: String,
        requirements: String,
        type: GameType
    ) {
        coroutineScope.launch {
            val createdGame =
                GameHubAPI.service.createGame(name, description, rules, requirements, type)
            try {
                _status.value = ApiStatus.LOADING
                val result = createdGame.await()
                _status.value = ApiStatus.DONE
                game = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                game = null
            }
        }
    }

    fun updateGame(
        id: String,
        name: String,
        description: String,
        rules: String,
        requirements: String,
        type: GameType
    ) {
        coroutineScope.launch {
            val updatedGame =
                GameHubAPI.service.updateGame(id, name, description, rules, requirements, type)
            try {
                _status.value = ApiStatus.LOADING
                val result = updatedGame.await()
                _status.value = ApiStatus.DONE
                game = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                game = null
            }
        }
    }

    fun deleteGame(id: String) {
        coroutineScope.launch {
            val deletedGame = GameHubAPI.service.deleteGame(id)
            try {
                _status.value = ApiStatus.LOADING
                var result = deletedGame.await()
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