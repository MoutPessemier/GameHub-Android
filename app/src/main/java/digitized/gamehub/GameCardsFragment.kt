package digitized.gamehub


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.databinding.GameCardsBinding


class GameCardsFragment : Fragment() {

    private lateinit var viewModel: GameCardsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameCardsBinding =
            DataBindingUtil.inflate(inflater, R.layout.game_cards, container, false)
        binding.btnInfo.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(GameCardsFragmentDirections.actionGameCardsFragmentToGamePartyInfoFragment())
        }

        viewModel = ViewModelProviders.of(this).get(GameCardsViewModel::class.java)

        return binding.root
    }


}
