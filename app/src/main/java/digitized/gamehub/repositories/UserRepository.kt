package digitized.gamehub.repositories

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import digitized.gamehub.database.GameHubDatabase
import digitized.gamehub.domain.User
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.LoginDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val database: GameHubDatabase, context: Context) {

    var _user: MutableLiveData<User>? = null
    val user: LiveData<User>?
        get() = _user

    private val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    suspend fun login(loginDTO: LoginDTO) {
        withContext(Dispatchers.IO) {
            val usr = GameHubAPI.service.login(loginDTO).await()
            sharedPreferences.edit().apply {
                putString("userId", usr.id)
                putString("token", "")
            }.apply()

            _user?.value = usr
        }
    }

    fun logout() {
        _user?.value = null
        sharedPreferences.edit().apply {
            putString("userId", "")
            putString("token", "")
        }.apply()
    }

    suspend fun updateAccount(user: User) {
        withContext(Dispatchers.IO) {
            val usr = GameHubAPI.service.updateUser(user).await()
            _user?.value = usr
        }
    }

    suspend fun deleteAccount(id: String) {
        withContext(Dispatchers.IO) {
            val userId = GameHubAPI.service.deleteUser(id).await()
        }
        logout()
    }
}