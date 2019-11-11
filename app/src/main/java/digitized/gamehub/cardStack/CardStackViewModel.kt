package digitized.gamehub.cardStack

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class CardStackViewModel(application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database)

    val parties = partyRepository.parties
    val games = gameRepository.games

    init {
        coroutineScope.launch {
            partyRepository.getPartiesNearYou(10, 50.0, 50.0)
            gameRepository.getGames()
            //both null for some reason --> logging requests shows true data though
            Timber.d(parties.value.toString())
            Timber.d(games.value.toString())
        }
    }

    fun refreshPartiesNearYou(){
        coroutineScope.launch {
            partyRepository.getPartiesNearYou(10,50.0, 50.0)
        }
    }

    fun joinParty() {
        TODO()
    }

    fun declineParty() {
        TODO()
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