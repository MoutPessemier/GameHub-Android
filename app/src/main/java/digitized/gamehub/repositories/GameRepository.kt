package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.Game
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDatabaseModel
import java.lang.Exception
import java.net.SocketTimeoutException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GameRepository(private val database: GameHubDatabase) {

    val games: LiveData<List<Game>> = Transformations.map(database.gameDao.getGames()) {
        it.asDomainModel()
    }

    /**
     * Gets all the games
     */
    suspend fun getGames() {
        withContext(Dispatchers.IO) {
            try {
                val games = GameHubAPI.service.getAllGames().await()
                database.gameDao.insertAll(*games.asDatabaseModel())
            } catch (e: SocketTimeoutException) {
                val games = GameHubAPI.service.getAllGames().await()
                database.gameDao.insertAll(*games.asDatabaseModel())
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    suspend fun clearDb() {
        withContext(Dispatchers.IO) {
            database.gameDao.clearAll()
        }
    }
}
