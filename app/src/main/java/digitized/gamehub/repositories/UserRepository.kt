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
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.LoginDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: GameHubDatabase) {

    private var _user: MutableLiveData<User>? = null
    val user: LiveData<User>?
        get() = _user

    suspend fun insertUser(user: User, lat: Double?, long: Double?) {
        withContext(Dispatchers.IO) {
            val entityUser = UserEntity(
                user.id!!,
//                user.firstName,
//                user.lastName,
//                user.telephone,
                user.email,
//                user.birthDate,
//                user.userRole,
//                user.password,
                user.maxDistance
                //lat, long
            )
            database.userDao.insertUser(entityUser)
        }
    }

//    suspend fun updateAccount(user: User, lat: Double?, long: Double?) {
//        withContext(Dispatchers.IO) {
//            val usr = GameHubAPI.service.updateUser(user).await()
//            val entityUser = UserEntity(
//                user.id!!,
//                user.firstName,
//                user.lastName,
//                user.telephone,
//                user.email,
//                user.birthDate,
//                user.userRole,
//                user.password,
//                user.maxDistance
//                //lat, long
//            )
//            database.userDao.update(entityUser)
//            _user?.value = usr
//        }
//    }

//    suspend fun deleteAccount(id: String) {
//        withContext(Dispatchers.IO) {
//            GameHubAPI.service.deleteUser(id).await()
//            database.userDao.clear()
//        }
//    }
}