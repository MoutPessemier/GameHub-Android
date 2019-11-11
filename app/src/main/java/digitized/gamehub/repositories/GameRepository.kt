package digitized.gamehub.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.Game
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GameRepository(private val database: GameHubDatabase){

    val games: LiveData<List<Game>> = Transformations.map(database.gameDao.getGames()){
        it.asDomainModel()
    }

    suspend fun getGames() {
        withContext(Dispatchers.IO){
            val games = GameHubAPI.service.getAllGames().await()
            database.gameDao.insertAll(*games.asDatabaseModel())
        }
    }
}