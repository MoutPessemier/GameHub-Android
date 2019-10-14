package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import digitized.gamehub.databinding.RegisterBinding

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: RegisterBinding =
            DataBindingUtil.inflate(inflater, R.layout.register, container, false)
        binding.btnToLogin.setOnClickListener { view: View ->
            view.findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }
        binding.btnRegister.setOnClickListener { view: View ->
            view.findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToGameCardsFragment2())
        }
        return binding.root
    }
}