package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.PartyInteractionDTO
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PartyRepository(private val database: GameHubDatabase) {

    val parties: LiveData<List<GameParty>> = Transformations.map(database.partyDao.getParties()) {
        it.asDomainModel()
    }

    suspend fun getPartiesNearYou(distance: Int, lat: Double, long: Double) {
        withContext(Dispatchers.IO) {
            val parties = GameHubAPI.service.getPatiesNearYou(distance, lat, long, "5db7f1fd89ecf72554ca9960").await()
            database.partyDao.insertAll(*parties.asDatabaseModel())
        }
    }

    suspend fun createParty(party: GameParty){
        val party = GameHubAPI.service.createParty(party).await()
        database.partyDao.insertAll()
    }

    suspend fun updateParty(party: GameParty){
        val party = GameHubAPI.service.updateParty(party).await()
        // database.partyDao.update(party)
    }

    suspend fun deleteParty(partyId: String){
        val id = GameHubAPI.service.deleteParty(partyId).await()
        database.partyDao.deleteParty(id)
    }

    suspend fun joinParty(partyInteractionDTO: PartyInteractionDTO){
        val party = GameHubAPI.service.joinParty(partyInteractionDTO).await()
        //database.partyDao.update(party)
    }

    suspend fun declineParty(partyInteractionDTO: PartyInteractionDTO){
        val party = GameHubAPI.service.declineParty(partyInteractionDTO).await()
        //database.partyDao.update(party)
    }
}