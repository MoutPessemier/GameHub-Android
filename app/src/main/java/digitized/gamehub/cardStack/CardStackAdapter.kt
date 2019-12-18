package digitized.gamehub.cardStack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import digitized.gamehub.R
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameParty
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CardStackAdapter(private var parties: List<GameParty> = emptyList()) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    var games = listOf<Game>()
        set(value) {
            field = value
        }

    var currentParty: GameParty? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.card_stack_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val party = parties[position]
        currentParty = party
        val game = games.first { g -> g.id == party.gameId }
        holder.partyName.text = party.name
        holder.partyWhen.text = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(party.date)
        holder.gameName.text = game.name
        holder.gameDescription.text = game.description
        // holder.partyWhere.text =
        // Glide.with(holder.gameImage).load().into(holder.gameImage)

    }
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val gameImage: ImageView = view.findViewById(R.id.img_game_card)
        val partyName: TextView = view.findViewById(R.id.txt_party_name_card)
        val gameName: TextView = view.findViewById(R.id.txt_game_name_card)
        val gameDescription: TextView = view.findViewById(R.id.txt_game_description_card)
        val partyWhen: TextView = view.findViewById(R.id.txt_party_when_card)
        // val partyWhere: TextView = view.findViewById(R.id.txt_party_where_card)
    }

    fun setParties(parties: List<GameParty>) {
        this.parties = parties
    }

    fun getParties(): List<GameParty> {
        return parties
    }

}
