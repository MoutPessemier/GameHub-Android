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

class CardStackAdapter() :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    var parties = listOf<GameParty>()
        set(value) {
            field = value
        }
    var games = listOf<Game>()
        set(value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.game_cards, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val party = parties.get(position)
        val game = games.filter { g -> g.id == party.gameId }.first()
        holder.partyWhen.text = party.date.toString()
        holder.gameName.text = game.name
        holder.gameDescription.text = game.description
        // holder.partyWhere.text =
        // Glide.with(holder.gameImage).load().into(holder.gameImage)


    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.img_game)
        val gameName: TextView = view.findViewById(R.id.txt_party_name)
        val gameDescription: TextView = view.findViewById(R.id.txt_game_description)
        val partyWhen: TextView = view.findViewById(R.id.txt_party_when)
        val partyWhere: TextView = view.findViewById(R.id.txt_party_where)
    }

}