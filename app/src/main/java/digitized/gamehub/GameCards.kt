package digitized.gamehub


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import digitized.gamehub.databinding.ActivityGameCardsBinding


/**
 * A simple [Fragment] subclass.
 */
class GameCards : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ActivityGameCardsBinding = DataBindingUtil.inflate(inflater, R.layout.activity_game_cards, container, false)
        return binding.root
    }


}
