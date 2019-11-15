package digitized.gamehub.account

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import digitized.gamehub.MainActivity
import digitized.gamehub.R
import digitized.gamehub.databinding.ActivityLoginBinding
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var auth0: Auth0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        auth0 = Auth0(this)
        auth0.isOIDCConformant = true

        binding.btnSignup.setOnClickListener {
            login()
        }

//        if (intent.getBooleanExtra(EXTRA_CLEAR_CREDENTIALS, false)) {
//            logout()
//        }
//        login()
    }

    private fun login() {
        WebAuthProvider.login(auth0)
            .withScheme("demo")
            .withAudience(
                String.format(
                    "https://%s/userinfo",
                    getString(R.string.com_auth0_domain)
                )
            )
            .start(this@LoginActivity, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    // Show error Dialog to user
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Error Logging In.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(exception: AuthenticationException) {
                    // Show error to user
                    Timber.d(exception.description)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onSuccess(credentials: Credentials) {
                    // Store credentials
                    val mainActivityIntent = Intent(application, MainActivity::class.java)
                    startActivity(mainActivityIntent)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Succesfully Logged In!", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }


//    private fun logout() {
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.putExtra(LoginActivity.EXTRA_CLEAR_CREDENTIALS, true)
//        startActivity(intent)
//        finish()
//    }

    private fun logout() {
        WebAuthProvider.logout(auth0)
            .withScheme("demo")
            .start(this, object : VoidCallback {
                override fun onSuccess(payload: Void) {}
                override fun onFailure(error: Auth0Exception) {
                    // Show error to user
                }
            })
    }


}

