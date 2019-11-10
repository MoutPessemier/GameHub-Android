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
import java.util.*
import kotlin.collections.ArrayList

class CardStackViewModel(application: Application): AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _parties = MutableLiveData<List<GameParty>>()
    val partiesNearYou: LiveData<List<GameParty>>
        get() = _parties

    private val _navigateToSelectedProperty = MutableLiveData<GameParty>()
    val navigateToSelectedProperty: LiveData<GameParty>
        get() = _navigateToSelectedProperty

    var party: GameParty? = null

    private val database = getInstance(application)
    private val gameRepository = GameRepository(database)
    private val partyRepository = PartyRepository(database)

    fun getPartiesNearYou(distance: Int, lat: Double, long: Double) {
        coroutineScope.launch {
            val parties = GameHubAPI.service.getPatiesNearYou(distance, lat, long)
            try {
                _status.value = ApiStatus.LOADING
                val resultList = parties.await()
                _status.value = ApiStatus.DONE
                //_parties.value = resultList
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _parties.value = ArrayList()
            }
        }
    }

    fun getPartiesNearYouMock(): List<GameParty> {
        return listOf<GameParty>(
            GameParty("1", "Temp1", Date(), 4, arrayOf("", "", ""), "2"),
            GameParty("2", "Temp2", Date(), 4, arrayOf("", "", ""), "2"),
            GameParty("3", "Temp2", Date(), 4, arrayOf("", "", ""), "2")
        )

        // ovelropen van u object, gehaald ID op, deze geef je mee aan viewmodel en die roept een repository op en returned de gameobject

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