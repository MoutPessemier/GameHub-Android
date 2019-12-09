package digitized.gamehub.accountSettings

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import digitized.gamehub.createGame.CreateGameViewModel
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountSettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    private val database = getInstance(application)
    private val userRepository = UserRepository(database)

    fun updateAccount(email:String, maxDistance: Int){
        coroutineScope.launch {
            val user = userRepository.user!!.value!!
            user.email = email
            user.maxDistance = maxDistance
            userRepository.updateAccount(user, user.latitude, user.longitude)
        }
    }

    fun deleteAccount(){
        coroutineScope.launch {
            val user = userRepository.user!!.value!!
            userRepository.deleteAccount(user.id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AccountSettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AccountSettingsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
