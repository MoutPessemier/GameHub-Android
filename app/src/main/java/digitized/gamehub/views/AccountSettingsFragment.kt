package digitized.gamehub.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.viewmodels.AccountSettingsViewModel
import digitized.gamehub.R
import digitized.gamehub.databinding.AccountSettingsBinding

class AccountSettingsFragment : Fragment() {

    //private lateinit var viewModel: AccountSettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AccountSettingsBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.account_settings, container, false)
//        binding.btnLogout.setOnClickListener { view: View ->
//            view.findNavController()
//                .navigate(AccountSettingsFragmentDirections.actionNavAccountViewToLoginFragment())
//        }
//
//        viewModel = ViewModelProviders.of(this).get(AccountSettingsViewModel::class.java)

        return binding.root
    }
}