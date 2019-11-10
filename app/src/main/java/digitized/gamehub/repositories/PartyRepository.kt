package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PartyRepository(private val database: GameHubDatabase) {

    val parties: LiveData<List<GameParty>> = Transformations.map(database.partyDao.getParties()) {
        it.asDomainModel()
    }

    suspend fun getPartiesNearYou(distance: Int, lat: Double, long: Double) {
        withContext(Dispatchers.IO) {
            val parties = GameHubAPI.service.getPatiesNearYou(distance, lat, long).await()
            database.partyDao.insertAll(*parties.asDatabaseModel())
        }
    }
}