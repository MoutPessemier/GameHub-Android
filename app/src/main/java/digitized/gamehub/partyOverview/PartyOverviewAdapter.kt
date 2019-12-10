package digitized.gamehub.partyOverview

import digitized.gamehub.databinding.PartyOverviewListItemBinding
import digitized.gamehub.domain.GameParty
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import digitized.gamehub.domain.Game
import java.text.SimpleDateFormat
import java.util.*

class PartyOverviewAdapter : ListAdapter<GameParty,
        PartyOverviewAdapter.ViewHolder>(PartyDiffCallback()) {

    var games: List<Game>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, games!!)
    }

    class ViewHolder private constructor(val binding: PartyOverviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameParty, games: List<Game>) {
            binding.txtPartyName.text = item.name
            binding.txtPartyWhen.text =
                SimpleDateFormat("dd/MM/YYYY", Locale.FRENCH).format(item.date)
            val game = games.find { game -> game.id == item.gameId }
            binding.txtGameName.text = game!!.name
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PartyOverviewListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PartyDiffCallback : DiffUtil.ItemCallback<GameParty>() {
    override fun areItemsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem == newItem
    }

}