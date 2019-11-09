package digitized.gamehub.cardStack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import digitized.gamehub.R
import digitized.gamehub.domain.GameParty

class CardStackAdapter(private var parties: List<GameParty>) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.game_cards, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parties.size
    }

    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        val party = parties[position]
        holder.partyWhen.text = party.date.toString()
        // I need to get my game here as well --> object al ophalen en meegeven aan de adapter
        // holder.gameName.text = party.gameId
        // holder.partyWhere.text =
        // Glide.with(holder.gameImage).load().into(holder.gameImage)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.img_game)
        val gameName: TextView = view.findViewById(R.id.txt_party_name)
        val gameDescription:TextView = view.findViewById(R.id.txt_game_description)
        val partyWhen:TextView = view.findViewById(R.id.txt_party_when)
        val partyWhere: TextView = view.findViewById(R.id.txt_party_where)
    }

    fun getParties(): List<GameParty> {
        return parties
    }

    fun setParties(parties: List<GameParty>) {
        this.parties = parties
    }

}