package digitized.gamehub.partyOverview

import digitized.gamehub.databinding.PartyOverviewListItemBinding
import digitized.gamehub.domain.GameParty
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class PartyOverviewAdapter : ListAdapter<GameParty,
        PartyOverviewAdapter.ViewHolder>(PartyDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PartyOverviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameParty) {
            binding.txtPartyName.text = item.name
            binding.txtPartyWhen.text =
                SimpleDateFormat("dd/MM/YYYY", Locale.FRENCH).format(item.date)
            binding.txtPartyWhere.text = ""
            binding.txtGameName.text = item.gameId
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

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class PartyDiffCallback : DiffUtil.ItemCallback<GameParty>() {
    override fun areItemsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem == newItem
    }
}