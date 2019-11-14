package digitized.gamehub.createGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.R
import digitized.gamehub.databinding.AddNewGameBinding
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType

class CreateGameFragment : Fragment() {

    private lateinit var viewModel: CreateGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddNewGameBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.add_new_game, container, false
            )

        viewModel = ViewModelProviders.of(this).get(CreateGameViewModel::class.java)

        binding.btnCreateGame.setOnClickListener { view: View ->
            val type: GameType =
                viewModel.convertToType(binding.sprGameType.selectedItem.toString())
            viewModel.createGame(
                Game(
                    null,
                    binding.txtGameName.text.toString(),
                    binding.txtGameDescription.text.toString(),
                    binding.txtGameRules.text.toString(),
                    binding.txtGameRequirement.text.toString(),
                    type
                )
            )
            if (viewModel.game != null) {
                view.findNavController()
                    .navigate(CreateGameFragmentDirections.actionNavCreateGameViewToGameCardsFragment())
            } else {
                // error message
            }

        }

        return binding.root
    }
}
