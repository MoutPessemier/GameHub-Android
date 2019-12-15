package digitized.gamehub.accountSettings

import android.app.Application
import androidx.lifecycle.*
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.domain.ApiStatus
import digitized.gamehub.domain.User
import digitized.gamehub.repositories.LoginRepository
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
    private var loginRepository= LoginRepository(application)

    //var user = loginRepository.user!!

    /**
     * Updates the user account
     */
    fun updateAccount(fistName:String, lastName: String, email:String, maxDistance: Int){
        coroutineScope.launch {
            loginRepository.user!!.firstName = fistName
            loginRepository.user!!.lastName = lastName
            loginRepository.user!!.email = email
            loginRepository.user!!.maxDistance = maxDistance
            userRepository.updateAccount(loginRepository.user!!)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * calls the logs the user out
     */
    fun logout() {
        loginRepository.logout()
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
