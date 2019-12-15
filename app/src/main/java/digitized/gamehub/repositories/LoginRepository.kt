package digitized.gamehub.repositories

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import digitized.gamehub.R
import digitized.gamehub.domain.User
import digitized.gamehub.network.AccessTokenRequest
import digitized.gamehub.network.Auth0API
import digitized.gamehub.network.DTO.LoginDTO
import digitized.gamehub.network.DTO.RegisterDTO
import digitized.gamehub.network.DTO.asDomainModel
import digitized.gamehub.network.GameHubAPI
import digitized.gamehub.network.asDomainModel
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.lang.Exception
import java.net.SocketTimeoutException

class LoginRepository(val context: Context) {

    var user: User? = null
        private set

    var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun login(accessToken: String) {
        runBlocking {
            var auth0id = Auth0API.service.getAccount("Bearer $accessToken").await()

            var body = AccessTokenRequest(
                context.resources.getString(R.string.com_auth0_management_id),
                context.resources.getString(R.string.com_auth0_management_secret),
                context.resources.getString(R.string.com_auth0_audience),
                "client_credentials"
            )

            var accessJWT = Auth0API.service.getAccessToken("application/json", body).await()

            var currentUser = Auth0API.service.getUser("Bearer " + accessJWT.access_token, auth0id.sub).await()

            val user = currentUser.asDomainModel()

            var userExists = false
            try {
                userExists = GameHubAPI.service.doesUserExist(user.email).await()
            } catch (e: SocketTimeoutException) {
                userExists = GameHubAPI.service.doesUserExist(user.email).await()
            } catch (e: Exception) {
                Timber.d(e)
                e.printStackTrace()
            }

            if (userExists) {
                val loginDTO = LoginDTO(user.email)
                try {
                    val nwuser = GameHubAPI.service.login(loginDTO).await()
                    setLoggedInUser(nwuser.asDomainModel())
                } catch (e: SocketTimeoutException) {
                    val nwuser = GameHubAPI.service.login(loginDTO).await()
                    setLoggedInUser(nwuser.asDomainModel())
                } catch (e: Exception) {
                    Timber.d(e)
                    e.printStackTrace()
                }
            } else {
                try {
                    val nwuser = GameHubAPI.service.register(
                        RegisterDTO(
                            user.email,
                            user.firstName,
                            user.lastName
                        )
                    ).await()
                    setLoggedInUser(nwuser.asDomainModel())
                } catch (e: SocketTimeoutException) {
                    val nwuser = GameHubAPI.service.register(
                        RegisterDTO(
                            user.email,
                            user.firstName,
                            user.lastName
                        )
                    ).await()
                    setLoggedInUser(nwuser.asDomainModel())
                } catch (e: Exception) {
                    Timber.d(e)
                    e.printStackTrace()
                }
            }

        }
    }

    fun logout() {
        user = null
        editor.apply {
            putString("userId", "")
            putString("displayName", "")
        }
        editor.commit()
    }

    private fun setLoggedInUser(user: User) {
        this.user = user
        editor.apply {
            putBoolean("loggedIn", true)
            putString("userId", user.id)
            putString("email", user.email)
        }
        editor.commit()
    }
}