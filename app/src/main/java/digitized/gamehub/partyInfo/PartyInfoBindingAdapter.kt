package digitized.gamehub.partyInfo

import android.widget.TextView
import androidx.databinding.BindingAdapter
import digitized.gamehub.domain.GameType
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("gameType")
fun bindGameType(textView: TextView, gameType: GameType?){
    textView.text = when(gameType){
        GameType.CARD_GAME -> "Card Game"
        GameType.BOARD_GAME -> "Board Game"
        GameType.DnD -> "Dungeons and Dragons"
        GameType.FAMILY_GAME -> "Family Game"
        GameType.PARTY_GAME -> "Party Game"
        GameType.VIDEO_GAME -> "Video Game"
        else -> "Unkown Genre"
    }
}

@BindingAdapter("partyDate")
fun bindDate(textView: TextView, date: Date?){
    val d = SimpleDateFormat("dd/MM/YYYY", Locale.ENGLISH).format(date!!)
    textView.text = d
}