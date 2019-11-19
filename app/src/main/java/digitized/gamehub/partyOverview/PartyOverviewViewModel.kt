package digitized.gamehub.partyOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import digitized.gamehub.createParty.CreatePartyViewModel
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PartyOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val database = getInstance(application)

    private val partyRepository = PartyRepository(database)
    val parties = partyRepository.parties

    private val gameRepository = GameRepository(database)
    val games = gameRepository.games

    init {
        coroutineScope.launch {
            partyRepository.getPartiesNearYou(1000, 51.0538286, 3.7250121, "5db8838eaffe445c66076a89")
            gameRepository.getGames()
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PartyOverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PartyOverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}