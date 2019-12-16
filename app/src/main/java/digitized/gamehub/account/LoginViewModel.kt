package digitized.gamehub.account

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import digitized.gamehub.database.GameHubDatabase.Companion.getInstance
import digitized.gamehub.repositories.LoginRepository

class LoginViewModel(val application: Application): ViewModel(){

    var auth0: Auth0 = Auth0(application)
    var credentialsManager: SecureCredentialsManager
    private val database = getInstance(application)
    private var loginRepository =  LoginRepository(application,database)

    init {

        //AUTH0
        auth0.isOIDCConformant = true

        credentialsManager = SecureCredentialsManager(
            application,
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(application)
        )
    }

    /**
     * Checks if Auth0 still has the credentials of the logged in user
     */
    fun hasValidCredentials(): Boolean{
        return credentialsManager.hasValidCredentials()
    }

    fun login(accessToken: String) {
        loginRepository.login(accessToken)
    }

    class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}