package digitized.gamehub.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.viewmodels.LoginViewModel
import digitized.gamehub.R
import digitized.gamehub.databinding.LoginBinding

class LoginFragment : Fragment() {

//    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.login, container, false)
//        binding.btnToRegister.setOnClickListener { view: View ->
//            view.findNavController()
//                .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
//        }
//        binding.btnLogin.setOnClickListener { view: View ->
//            view.findNavController()
//                .navigate(LoginFragmentDirections.actionLoginFragmentToGameCardsFragment2())
//        }
//
//        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        return binding.root
    }
}