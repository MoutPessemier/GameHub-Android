package digitized.gamehub.createParty

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import digitized.gamehub.R
import digitized.gamehub.domain.Game

@BindingAdapter("gameNames")
fun Spinner.bindGameNames(games: List<Game>) {
    val names = games.map {
        it.name
    }
    val spinnerAdapter = ArrayAdapter<String>(context, R.layout.create_game_party, R.id.txt_party_game, names) // nop how does this stuff work?
    this.adapter = spinnerAdapter
}
