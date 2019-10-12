package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import digitized.gamehub.databinding.GamePartyInfoBinding

class GamePartyInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GamePartyInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.game_party_info, container, false)
        return binding.root
    }
}