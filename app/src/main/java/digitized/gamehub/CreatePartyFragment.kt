package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.databinding.CreateGamePartyBinding

class CreatePartyFragment : Fragment() {

    private lateinit var viewModel: CreatePartyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CreateGamePartyBinding =
            DataBindingUtil.inflate(inflater, R.layout.create_game_party, container, false)
        binding.btnCreateGamParty.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(CreateGameFragmentDirections.actionCreateGameFragmentToGameCardsFragment())
        }

        viewModel = ViewModelProviders.of(this).get(CreatePartyViewModel::class.java)

        return binding.root
    }
}