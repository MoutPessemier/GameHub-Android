package digitized.gamehub.createParty

import android.app.Application
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import digitized.gamehub.repositories.GameRepository
import java.util.*
import digitized.gamehub.domain.Location
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDomainModel
import digitized.gamehub.repositories.PartyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
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
    var currentLocation = LatLng(0.0, 0.0)
//    var place: Place? = null

    /**
     * Creates a new party
     */
    fun createParty(partyName: String, whenDate: Date, maxSize: Int): Boolean {
        val createdParty = GameParty(
            null,
            partyName,
            whenDate,
            maxSize,
            arrayOf("5db8838eaffe445c66076a88"),
            arrayOf(),
            "5dd0200902611d001e96838e",
            Location(
                "Point",
                doubleArrayOf(currentLocation.latitude, currentLocation.longitude)
            )
        )
        try {
            coroutineScope.launch {
                _status.value = ApiStatus.LOADING
                val addedParty = GameHubAPI.service.createParty(createdParty).await()
                _status.value = ApiStatus.DONE
                partyRepository.createParty(addedParty.asDomainModel())
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

