package digitized.gamehub.accountSettings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import digitized.gamehub.R
import digitized.gamehub.account.LoginActivity
import digitized.gamehub.databinding.AccountSettingsBinding
import timber.log.Timber

class AccountSettingsFragment : Fragment() {

    private lateinit var viewModel: AccountSettingsViewModel
    private lateinit var auth0: Auth0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AccountSettingsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.account_settings, container, false
            )

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, AccountSettingsViewModel.Factory(application))
            .get(AccountSettingsViewModel::class.java)
        binding.viewModel = viewModel

        // Auth0
        val context = requireContext()
        auth0 = Auth0(context)
        auth0.isOIDCConformant = true


        binding.btnLogout.setOnClickListener {
            logout()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun logout() {
        WebAuthProvider.logout(auth0)
            .withScheme("demo")
            .start(context, object : VoidCallback {
                override fun onSuccess(payload: Void) {
                    Timber.d(payload.toString())
                }
                override fun onFailure(error: Auth0Exception) {
                    // Show error to user
                    Timber.d(error)
                    Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                }
            })
    }
}
