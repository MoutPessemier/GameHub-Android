package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import digitized.gamehub.databinding.AccountSettingsBinding

class AccountSettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AccountSettingsBinding =
            DataBindingUtil.inflate(inflater, R.layout.account_settings, container, false)
        binding.btnLogout.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_accountSettingsFragment2_to_loginFragment)
        }
        return binding.root
    }
}