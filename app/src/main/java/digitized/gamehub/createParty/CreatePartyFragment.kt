package digitized.gamehub.createParty

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import digitized.gamehub.R
import digitized.gamehub.databinding.CreateGamePartyBinding


class CreatePartyFragment : Fragment() {

    private lateinit var viewModel: CreatePartyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CreateGamePartyBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.create_game_party, container, false
            )

        // val spinnerAdapter = ArrayAdapter<String>(context!!) do I need this?
        // binding.sprPartyGame.adapter = spinnerAdapter

        binding.btnCreateGamParty.setOnClickListener { view: View ->
            //viewModel.createParty(binding.txtPartyName.text.toString(), SimpleDateFormat(binding.txtPartyDate.text.toString()))
            view.findNavController()
                .navigate(CreatePartyFragmentDirections.actionNavCreatePartyViewToGameCardsFragment())
        }

        binding.materialButton.setOnClickListener{
            val activity = Intent(context, AutocompleteActivity::class.java)
            startActivity(activity)
        }

        viewModel = ViewModelProviders.of(this).get(CreatePartyViewModel::class.java)
        //binding.viewModel = viewModel

        return binding.root
    }
}
