package digitized.gamehub.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.viewmodels.GamePartyInfoViewModel
import digitized.gamehub.R
import digitized.gamehub.databinding.GamePartyInfoBinding

class GamePartyInfoFragment : Fragment() {

    private lateinit var viewModel: GamePartyInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GamePartyInfoBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.game_party_info, container, false)
        binding.btnJoinParty.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GamePartyInfoFragmentDirections.actionGamePartyInfoFragmentToGameCardsFragment())
        }

        viewModel = ViewModelProviders.of(this).get(GamePartyInfoViewModel::class.java)

        return binding.root
    }
}