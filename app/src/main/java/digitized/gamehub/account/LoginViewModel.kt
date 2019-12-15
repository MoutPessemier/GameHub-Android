package digitized.gamehub.account

import digitized.gamehub.repositories.LoginRepository
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage

class LoginViewModel(val application: Application): ViewModel(){

    var loginRepository: LoginRepository = LoginRepository(application)
    var auth0: Auth0 = Auth0(application)
    var credentialsManager: SecureCredentialsManager

    init {

        //AUTH0
        auth0.isOIDCConformant = true

        credentialsManager = SecureCredentialsManager(
            application,
            AuthenticationAPIClient(auth0),
            SharedPreferencesStorage(application)
        )
    }

    fun hasValidCredentials(): Boolean{
        return credentialsManager.hasValidCredentials()
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