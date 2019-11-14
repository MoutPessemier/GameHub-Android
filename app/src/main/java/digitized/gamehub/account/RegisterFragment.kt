package digitized.gamehub.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import digitized.gamehub.MainActivity
import digitized.gamehub.R
import digitized.gamehub.databinding.RegisterBinding


class RegisterFragment : Fragment() {

//    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: RegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.register, container, false)
        binding.btnToLogin.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener { view: View ->
            val mainActivity = Intent(this.activity, MainActivity::class.java)
            startActivity(mainActivity)
        }

//        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        return binding.root
    }
}
