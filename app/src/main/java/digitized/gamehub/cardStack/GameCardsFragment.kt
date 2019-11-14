package digitized.gamehub.cardStack


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.R
import digitized.gamehub.databinding.GameCardsBinding
import digitized.gamehub.domain.GameParty
import java.util.*


class GameCardsFragment : Fragment() {

    private lateinit var viewModel: GameCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameCardsBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.game_cards, container, false)

        viewModel = ViewModelProviders.of(this).get(GameCardsViewModel::class.java)

        binding.btnInfo.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GameCardsFragmentDirections.actionGameCardsFragmentToGamePartyInfoFragment(
                    GameParty("1", "Temp", Date(), 4, arrayOf("11"),"5db76b7430957f0ef05e73fa")
                ))
        }

        return binding.root
    }


}
