package digitized.gamehub


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import digitized.gamehub.databinding.GameCardsBinding


class GameCardsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameCardsBinding =
            DataBindingUtil.inflate(inflater, R.layout.game_cards, container, false)
        binding.btnInfo.setOnClickListener { view:View -> view } // to GamePartyInfo screen
        return binding.root
    }


}
