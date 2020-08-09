package digitized.gamehub.account

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import digitized.gamehub.MainActivity
import digitized.gamehub.R
import digitized.gamehub.databinding.ActivityLoginBinding
import timber.log.Timber

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProviders.of(this, LoginViewModel.LoginViewModelFactory(application))
            .get(LoginViewModel::class.java)

        if (intent.getBooleanExtra("CLEAR_CREDENTIALS", false)) {
            logout()
            return
        }

        if (viewModel.hasValidCredentials()) {
            goToMainActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnSignup.isEnabled = true
        binding.btnSignup.setOnClickListener {
            login()
        }
    }

    /**
     * Redirects to the main activity
     */
    private fun goToMainActivity() {
        viewModel.credentialsManager.getCredentials(object :
            BaseCallback<Credentials, CredentialsManagerException> {

            override fun onSuccess(credentials: Credentials?) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.putExtra("ACCESS_TOKEN", credentials!!.accessToken)
                startActivity(intent)
                finish()
            }

            override fun onFailure(error: CredentialsManagerException?) {
                finish()
            }
        })
    }

    /**
     * Login method used to log a user in using Auth0
     */
    private fun login() {
        runOnUiThread {
            binding.btnSignup.isEnabled = false
            binding.btnSignup.isClickable = false
            binding.btnSignup.background = resources.getDrawable(R.drawable.disabled_button)
        }
        WebAuthProvider.login(viewModel.auth0)
            .withScheme("demo")
            .withAudience(
                String.format(
                    "https://%s/userinfo",
                    getString(R.string.com_auth0_domain)
                )
            )
            .start(this@LoginActivity, object : AuthCallback {
                override fun onFailure(dialog: Dialog) {
                    runOnUiThread {
                        // Show error Dialog to user
                        binding.btnSignup.isEnabled = true
                        binding.btnSignup.isClickable = true
                        binding.btnSignup.background = resources.getDrawable(R.drawable.custom_button_green)
                        Toast.makeText(applicationContext, "Error Logging In.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(exception: AuthenticationException) {
                    // Show error to user
                    Timber.d(exception.description)
                    binding.btnSignup.isEnabled = true
                    binding.btnSignup.isClickable = true
                    binding.btnSignup.background = resources.getDrawable(R.drawable.custom_button_green)
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            "Something Went Wrong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onSuccess(credentials: Credentials) {
                    try {
                        viewModel.login(credentials.accessToken!!)
                        viewModel.credentialsManager.saveCredentials(credentials)
                        goToMainActivity()
                    } catch (e: Exception) {
                        binding.btnSignup.isEnabled = true
                        binding.btnSignup.isClickable = true
                        binding.btnSignup.background = resources.getDrawable(R.drawable.custom_button_green)
                        Timber.d(e)
                    }
                }
            })
    }

    /**
     * Method used to log a user out using Auth0
     */
    private fun logout() {
        WebAuthProvider.logout(viewModel.auth0)
            .withScheme("demo")
            .start(this, object : VoidCallback {
                override fun onSuccess(payload: Void?) {
                    viewModel.credentialsManager.clearCredentials()
                }

                override fun onFailure(error: Auth0Exception) {
                    Toast.makeText(applicationContext, "failed to log user out", Toast.LENGTH_LONG)
                        .show()
                    goToMainActivity()
                }
            })
    }
}
