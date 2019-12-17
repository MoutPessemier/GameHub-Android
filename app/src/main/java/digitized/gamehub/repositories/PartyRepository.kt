package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.PartyEntity
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.GameParty
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import java.net.SocketTimeoutException

class PartyRepository(private val database: GameHubDatabase) {

    val parties = Transformations.map(database.partyDao.getParties()) {
        it.asDomainModel()
    }

    val joinedParties: LiveData<List<GameParty>> =
        Transformations.map(database.partyDao.getJoinedParties("5db8838eaffe445c66076a88")) {
            it.asDomainModel()
        }

    /**
     * Gets all parties near you
     */
    suspend fun getPartiesNearYou(distance: Int, lat: Double, long: Double, userId: String) {
        withContext(Dispatchers.IO) {
            try {
                val parties =
                    GameHubAPI.service.getPatiesNearYou(distance, lat, long, userId).await()
                database.partyDao.insertAll(*parties.asDatabaseModel())
            } catch (e: SocketTimeoutException) {
                val parties =
                    GameHubAPI.service.getPatiesNearYou(distance, lat, long, userId).await()
                database.partyDao.insertAll(*parties.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    suspend fun getJoinedParties(userId: String) {
        withContext(Dispatchers.IO) {
            try {
                val parties = GameHubAPI.service.getJoinedParties(userId).await()
                database.partyDao.insertAll(*parties.asDatabaseModel())
            } catch (e: SocketTimeoutException) {
                val parties = GameHubAPI.service.getJoinedParties(userId).await()
                database.partyDao.insertAll(*parties.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    /**
     * Creates a new party
     */
    suspend fun createParty(party: GameParty) {
        withContext(Dispatchers.IO) {
            try {
                val party = GameHubAPI.service.createParty(party).await()
                database.partyDao.insertAll(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: SocketTimeoutException) {
                val party = GameHubAPI.service.createParty(party).await()
                database.partyDao.insertAll(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    /**
     * Joins a party
     */
    suspend fun joinParty(partyInteractionDTO: PartyInteractionDTO) {
        withContext(Dispatchers.IO) {
            try {
                val party = GameHubAPI.service.joinParty(partyInteractionDTO).await()
                database.partyDao.update(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: SocketTimeoutException) {
                val party = GameHubAPI.service.joinParty(partyInteractionDTO).await()
                database.partyDao.update(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    /**
     * Declines a party
     */
    suspend fun declineParty(partyInteractionDTO: PartyInteractionDTO) {
        withContext(Dispatchers.IO) {
            try {
                val party = GameHubAPI.service.declineParty(partyInteractionDTO).await()
                database.partyDao.update(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: SocketTimeoutException) {
                val party = GameHubAPI.service.declineParty(partyInteractionDTO).await()
                database.partyDao.update(
                    PartyEntity(
                        party.id,
                        party.name,
                        party.date,
                        party.maxSize,
                        party.participants,
                        party.declines,
                        party.gameId,
                        party.location
                    )
                )
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    suspend fun clearDb() {
        withContext(Dispatchers.IO) {
            database.partyDao.clearAll()
        }
    }
}