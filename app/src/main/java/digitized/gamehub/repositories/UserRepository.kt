package digitized.gamehub.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import digitized.gamehub.database.GameHubDatabase
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

    private var _user: MutableLiveData<User>? = null
    val user: LiveData<User>?
        get() = _user

    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            val entityUser = user.asDatabaseModel()
            database.userDao.insertUser(entityUser)
        }
    }

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

    suspend fun deleteAccount(id: String) {
        withContext(Dispatchers.IO) {
            try {
                GameHubAPI.service.deleteUser(id).await()
                database.userDao.clear()
            } catch (e: SocketTimeoutException) {
                GameHubAPI.service.deleteUser(id).await()
                database.userDao.clear()
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }
        }
    }
}