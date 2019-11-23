package digitized.gamehub.createParty

import android.app.Application
import androidx.lifecycle.*
import com.google.android.libraries.places.api.model.Place
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import digitized.gamehub.repositories.GameRepository
import java.util.*
import digitized.gamehub.domain.Location
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class CreatePartyViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database)

    val games = gameRepository.games

    var game: Game? = null
    var place: Place? = null

    fun createParty(partyName: String, whenDate: Date, maxSize: Int): Boolean {
        val createdParty = GameParty(
            null,
            partyName,
            whenDate,
            maxSize,
            arrayOf<String>(""),
            game!!.id!!,
            Location(
                "Point",
                doubleArrayOf(place!!.latLng!!.latitude, place!!.latLng!!.longitude)
            )
        )
        try {
            coroutineScope.launch {
                _status.value = ApiStatus.LOADING
                val addedParty = GameHubAPI.service.createParty(createdParty).await()
                _status.value = ApiStatus.DONE
                val domainParty = GameParty(
                    addedParty.id,
                    addedParty.name,
                    addedParty.date,
                    addedParty.maxSize,
                    addedParty.participants,
                    addedParty.gameId,
                    addedParty.location
                )
                partyRepository.createParty(domainParty)

            }
            return true
        } catch (e: Exception) {
            _status.value = ApiStatus.ERROR
        }
        return false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreatePartyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreatePartyViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

