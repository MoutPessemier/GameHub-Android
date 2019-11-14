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

class CardStackAdapter(private var parties: List<GameParty>?, private var games: List<Game>?) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.game_cards, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (parties != null) parties!!.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (parties != null && games != null || parties?.size == 0 || games?.size == 0) {
            val party = parties!!.get(position)
            val game = games!!.filter { g -> g.id == party.gameId }.firstOrNull()
            if (game != null) {
                holder.partyWhen.text = party.date.toString()
                holder.gameName.text = game.name
                holder.gameDescription.text = game.description
                // holder.partyWhere.text =
                // Glide.with(holder.gameImage).load().into(holder.gameImage)
            }
        } else {
            //let the user know that there are not parties

        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.img_game)
        val gameName: TextView = view.findViewById(R.id.txt_party_name)
        val gameDescription: TextView = view.findViewById(R.id.txt_game_description)
        val partyWhen: TextView = view.findViewById(R.id.txt_party_when)
        val partyWhere: TextView = view.findViewById(R.id.txt_party_where)
    }

    fun getParties(): List<GameParty>? {
        return parties
    }

    fun setParties(parties: List<GameParty>) {
        this.parties = parties
    }

}
