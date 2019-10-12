package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import digitized.gamehub.databinding.CreateGamePartyBinding

class CreatePartyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CreateGamePartyBinding =
            DataBindingUtil.inflate(inflater, R.layout.create_game_party, container, false)
        return binding.root
    }
}