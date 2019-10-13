package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import digitized.gamehub.databinding.LoginBinding

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.login, container, false)
        binding.btnToRegister.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_gameCardsFragment2)
        }
        return binding.root
    }
}