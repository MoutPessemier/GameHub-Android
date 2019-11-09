package digitized.gamehub.cardStack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameCardsViewModel() : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    var party: GameParty? = null


    fun joinParty(partyId: String, userId: String) {
        coroutineScope.launch {
            val partyToJoin = GameHubAPI.service.joinParty(partyId, userId)
            try {
                _status.value = ApiStatus.LOADING
                val result = partyToJoin.await()
                _status.value = ApiStatus.DONE
                party = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                party = null
            }
        }
    }

    fun declineParty(partyId: String, userId: String) {
        coroutineScope.launch {
            val partyToDecline = GameHubAPI.service.declineParty(partyId, userId)
            try {
                _status.value = ApiStatus.LOADING
                val result = partyToDecline.await()
                _status.value = ApiStatus.DONE
                party = result
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
}