package digitized.gamehub.account

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.User
import digitized.gamehub.domain.UserRole
import digitized.gamehub.network.GameHubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.applicationContext.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private val database = getInstance(application)

    fun register(
        firstName: String,
        lastName: String,
        telephone: String,
        email: String,
        birthDate: Date,
        userRole: UserRole,
        password: String,
        maxDistance: Int
    ) {
        coroutineScope.launch {
            val register = GameHubAPI.service.register(
                User(
                    null,
                    firstName,
                    lastName,
                    telephone,
                    email,
                    birthDate,
                    userRole,
                    password,
                    maxDistance
                )
            )
            try {
                _status.value = ApiStatus.LOADING
                val result = register.await()
                _status.value = ApiStatus.DONE
                _user.value = result
                sharedPreferences.edit().apply {
                    putBoolean("isLoggedIn", true)
                    putString("userId", result.id)
                }.apply()
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _user.value = null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
