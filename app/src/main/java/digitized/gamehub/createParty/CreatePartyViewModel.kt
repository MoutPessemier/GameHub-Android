package digitized.gamehub.createParty

import android.app.Application
import androidx.lifecycle.*
import com.google.android.libraries.places.api.model.Place
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.GameParty
import digitized.gamehub.repositories.GameRepository

class CreatePartyViewModel(application: Application) : AndroidViewModel(application) {

    private val _place = MutableLiveData<Place>()
    val place: LiveData<Place>
        get() = _place

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)

    val games = gameRepository.games

    fun setPlace(place: Place) {
        _place.value = place
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

