package digitized.gamehub.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import digitized.gamehub.MainActivity
import digitized.gamehub.R
import digitized.gamehub.databinding.LoginBinding
import timber.log.Timber

class LoginFragment : Fragment() {

//    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: LoginBinding =
            DataBindingUtil.inflate<LoginBinding>(inflater, R.layout.login, container, false)

        binding.btnToRegister.setOnClickListener { view: View ->
            view.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.btnLogin.setOnClickListener {
            val mainActivity = Intent(this.activity, MainActivity::class.java)
            startActivity(mainActivity)
        }

//        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        return binding.root
    }
}