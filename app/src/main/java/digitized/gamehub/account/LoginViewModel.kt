package digitized.gamehub.account

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.User
import digitized.gamehub.network.GameHubAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        application.applicationContext.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    fun login(email: String, password: String) {
        coroutineScope.launch {
            val user = GameHubAPI.service.login(email, password)
            try {
                _status.value = ApiStatus.LOADING
                val result = user.await()
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
}