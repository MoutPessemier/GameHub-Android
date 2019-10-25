package digitized.gamehub.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.viewmodels.CreateGameViewModel
import digitized.gamehub.R
import digitized.gamehub.databinding.AddNewGameBinding

class CreateGameFragment : Fragment() {

    private lateinit var viewModel: CreateGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddNewGameBinding =
            DataBindingUtil.inflate(inflater,
                R.layout.add_new_game, container, false)
        binding.btnCreateGame.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(CreateGameFragmentDirections.actionCreateGameFragmentToGameCardsFragment())
        }

        viewModel = ViewModelProviders.of(this).get(CreateGameViewModel::class.java)

        return binding.root
    }
}