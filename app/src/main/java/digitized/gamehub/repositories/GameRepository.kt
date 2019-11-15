package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameEntity
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.Game
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

class GameRepository(private val database: GameHubDatabase) {

    val games: LiveData<List<Game>> = Transformations.map(database.gameDao.getGames()) {
        it.asDomainModel()
    }

    suspend fun getGames() {
        withContext(Dispatchers.IO) {
            val games = GameHubAPI.service.getAllGames().await()
            database.gameDao.insertAll(*games.asDatabaseModel())
        }
    }

    suspend fun createGame(game: Game) {
        withContext(Dispatchers.IO) {
            val game = GameHubAPI.service.createGame(game).await()
            database.gameDao.insertAll(
                GameEntity(
                    game.id,
                    game.name,
                    game.description,
                    game.rules,
                    game.requirements,
                    game.type,
                    game.visible
                )
            )
        }
    }

    suspend fun updateGame(game: Game) {
        withContext(Dispatchers.IO) {
            val game = GameHubAPI.service.updateGame(game).await()
            database.gameDao.update(
                GameEntity(
                    game.id,
                    game.name,
                    game.description,
                    game.rules,
                    game.requirements,
                    game.type,
                    game.visible
                )
            )
        }
    }

    suspend fun deleteGame(gameId: String) {
        withContext(Dispatchers.IO) {
            val id = GameHubAPI.service.deleteGame(gameId).await()
            database.gameDao.deleteGame(id)
        }
    }
}