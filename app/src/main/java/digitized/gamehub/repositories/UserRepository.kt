package digitized.gamehub.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.database.UserEntity
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.User
import digitized.gamehub.domain.asDatabaseModel
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.LoginDTO
import digitized.gamehub.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: GameHubDatabase) {

    private var _user: MutableLiveData<User>? = null
    val user: LiveData<User>?
        get() = _user

    suspend fun insertUser(user: User, latitude: Double?, longitude: Double?) {
        withContext(Dispatchers.IO) {
            val entityUser = user.asDatabaseModel()
            database.userDao.insertUser(entityUser)
        }
    }

    suspend fun updateAccount(user: User, latitude: Double?, longitude: Double?) {
        withContext(Dispatchers.IO) {
            val usr = GameHubAPI.service.updateUser(user).await()
            val entityUser = usr.asDatabaseModel()
            database.userDao.update(entityUser)
        }
    }

    suspend fun deleteAccount(id: String) {
        withContext(Dispatchers.IO) {
            GameHubAPI.service.deleteUser(id).await()
            database.userDao.clear()
        }
    }
}