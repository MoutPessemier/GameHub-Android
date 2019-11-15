package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.PartyEntity
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.PartyInteractionDTO
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PartyRepository(private val database: GameHubDatabase) {

    val parties: LiveData<List<GameParty>> = Transformations.map(database.partyDao.getParties()) {
        it.asDomainModel()
    }

    suspend fun getPartiesNearYou(distance: Int, lat: Double, long: Double, userId: String) {
        withContext(Dispatchers.IO) {
            val parties = GameHubAPI.service.getPatiesNearYou(distance, lat, long, userId).await()
            database.partyDao.insertAll(*parties.asDatabaseModel())
        }
    }

    suspend fun createParty(party: GameParty) {
        withContext(Dispatchers.IO) {
            val party = GameHubAPI.service.createParty(party).await()
            database.partyDao.insertAll(
                PartyEntity(
                    party.id,
                    party.name,
                    party.date,
                    party.maxSize,
                    party.participants,
                    party.gameId,
                    party.location
                )
            )
        }
    }

    suspend fun updateParty(party: GameParty) {
        withContext(Dispatchers.IO) {
            val party = GameHubAPI.service.updateParty(party).await()
            database.partyDao.update(
                PartyEntity(
                    party.id,
                    party.name,
                    party.date,
                    party.maxSize,
                    party.participants,
                    party.gameId,
                    party.location
                )
            )
        }
    }

    suspend fun deleteParty(partyId: String) {
        withContext(Dispatchers.IO) {
            val id = GameHubAPI.service.deleteParty(partyId).await()
            database.partyDao.deleteParty(id)
        }

    }

    suspend fun joinParty(partyInteractionDTO: PartyInteractionDTO) {
        withContext(Dispatchers.IO) {
            val party = GameHubAPI.service.joinParty(partyInteractionDTO).await()
            database.partyDao.update(
                PartyEntity(
                    party.id,
                    party.name,
                    party.date,
                    party.maxSize,
                    party.participants,
                    party.gameId,
                    party.location
                )
            )
        }
    }

    suspend fun declineParty(partyInteractionDTO: PartyInteractionDTO) {
        withContext(Dispatchers.IO) {
            val party = GameHubAPI.service.declineParty(partyInteractionDTO).await()
            database.partyDao.update(
                PartyEntity(
                    party.id,
                    party.name,
                    party.date,
                    party.maxSize,
                    party.participants,
                    party.gameId,
                    party.location
                )
            )
        }
    }
}