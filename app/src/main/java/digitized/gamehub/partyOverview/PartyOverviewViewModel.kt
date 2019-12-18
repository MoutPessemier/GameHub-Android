package digitized.gamehub.partyOverview

import android.app.Application
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import digitized.gamehub.createParty.CreatePartyViewModel
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.repositories.GameRepository
import digitized.gamehub.repositories.PartyRepository
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PartyOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    private val database = getInstance(application)

    private val partyRepository = PartyRepository(database, sharedPreferences.getString("userId", "")!!)
    val parties = partyRepository.joinedParties

    private val gameRepository = GameRepository(database)
    val games = gameRepository.games

    private val userRepository = UserRepository(database)
    val user = userRepository.user

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getJoinedParties(userId: String) {
        coroutineScope.launch {
            partyRepository.getJoinedParties(userId)
        }
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