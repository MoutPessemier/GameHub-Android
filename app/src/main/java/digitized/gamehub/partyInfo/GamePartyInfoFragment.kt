package digitized.gamehub.partyInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
            DataBindingUtil.inflate(
                inflater,
                R.layout.game_party_info, container, false
            )
        binding.lifecycleOwner = this


        val party = GamePartyInfoFragmentArgs.fromBundle(arguments!!).selectedParty

        // ViewModel
        val application = requireNotNull(this.activity).application
        viewModel =
            ViewModelProviders.of(this, GamePartyInfoViewModel.Factory(party, application))
                .get(GamePartyInfoViewModel::class.java)

        binding.viewModel = viewModel

        binding.btnJoinParty.setOnClickListener { view: View ->
            // viewModel.joinParty(party.id, "")
            findNavController().navigate(GamePartyInfoFragmentDirections.actionGamePartyInfoFragmentToGameCardsFragmentJoin())
        }

        binding.btnDeclineParty.setOnClickListener { view: View ->
            //decline in some way
            findNavController().navigate(GamePartyInfoFragmentDirections.actionGamePartyInfoFragmentToGameCardsFragmentDecline())
        }


        return binding.root
    }
}