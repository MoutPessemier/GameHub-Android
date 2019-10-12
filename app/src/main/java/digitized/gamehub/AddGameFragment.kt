package digitized.gamehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import digitized.gamehub.databinding.AddNewGameBinding

class AddGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddNewGameBinding =
            DataBindingUtil.inflate(inflater, R.layout.add_new_game, container, false)
        return binding.root
    }
}