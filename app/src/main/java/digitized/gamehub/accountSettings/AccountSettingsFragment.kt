package digitized.gamehub.accountSettings

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import digitized.gamehub.MainActivity
import digitized.gamehub.R
import digitized.gamehub.account.LoginActivity
import digitized.gamehub.databinding.AccountSettingsFragmentBinding
import digitized.gamehub.domain.User
import timber.log.Timber

class AccountSettingsFragment : Fragment() {

    private lateinit var binding: AccountSettingsFragmentBinding
    private lateinit var viewModel: AccountSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.account_settings_fragment, container, false
            )

        // ViewModel
        val application = requireNotNull(activity).application
        viewModel = ViewModelProviders.of(this, AccountSettingsViewModel.Factory(application))
            .get(AccountSettingsViewModel::class.java)
        binding.viewModel = viewModel

        // supported screen orientaion
        activity!!.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnSaveUpdates.setOnClickListener { view ->
            viewModel.updateAccount(
                binding.txtFirstName.text.toString(),
                binding.txtLastName.text.toString(),
                binding.txtUserEmail.text.toString(),
                binding.maxDistance.progress
            )
            view.findNavController()
                .navigate(AccountSettingsFragmentDirections.actionAccountSettingsFragmentToCardStackFragment())
        }

        viewModel.user.observe(this, Observer {
            viewModel.usr = it
            updateUI(it)
        })

        binding.btnLogout.setOnClickListener {
            viewModel.logout()

            val loginActivity = Intent(activity, LoginActivity::class.java)
            loginActivity.putExtra("CLEAR_CREDENTIALS", true)
            startActivity(loginActivity)

            (activity as MainActivity).finish()
        }
    }

    private fun updateUI(user: User) {
        binding.txtFirstName.text = Editable.Factory().newEditable(user.firstName)
        binding.txtLastName.text = Editable.Factory().newEditable(user.lastName)
        binding.txtUserEmail.text = Editable.Factory().newEditable(user.email)
        binding.maxDistance.progress = user.maxDistance
    }
}
