package digitized.gamehub.partyOverview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import digitized.gamehub.createParty.CreatePartyViewModel
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository

class PartyOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getInstance(application)

    private val partyRepository = PartyRepository(database)
    val parties = partyRepository.parties

    private val gameRepository = GameRepository(database)
    val games = gameRepository.games


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