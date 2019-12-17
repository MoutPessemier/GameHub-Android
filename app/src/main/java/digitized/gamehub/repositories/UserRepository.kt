package digitized.gamehub.repositories

import androidx.lifecycle.Transformations
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.asDomainModel
import digitized.gamehub.domain.User
import digitized.gamehub.domain.asDatabaseModel
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import java.net.SocketTimeoutException

class UserRepository(private val database: GameHubDatabase) {

    val user = Transformations.map(database.userDao.getUser()) {
        it.asDomainModel()
    }

    /**
     * Inserts the logged in user into the database
     */
    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            val entityUser = user.asDatabaseModel()
            database.userDao.clear()
            database.userDao.insertUser(entityUser)
        }
    }

    /**
     * Updates the user's account
     */
    suspend fun updateAccount(user: User) {
        withContext(Dispatchers.IO) {
            try {
                val usr = GameHubAPI.service.updateUser(user).await()
                val entityUser = usr.asDatabaseModel()
                database.userDao.update(entityUser)
            } catch (e: SocketTimeoutException) {
                val usr = GameHubAPI.service.updateUser(user).await()
                val entityUser = usr.asDatabaseModel()
                database.userDao.update(entityUser)
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }

    suspend fun clearDb() {
        withContext(Dispatchers.IO) {
            database.userDao.clear()
        }
    }
}