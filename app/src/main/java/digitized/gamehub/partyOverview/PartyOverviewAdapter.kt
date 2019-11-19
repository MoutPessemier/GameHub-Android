package digitized.gamehub.partyOverview

import digitized.gamehub.databinding.PartyOverviewListItemBinding
import digitized.gamehub.domain.GameParty
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class PartyOverviewAdapter : ListAdapter<GameParty,
        PartyOverviewAdapter.ViewHolder>(PartyDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Timber.d("HAAAAAAAAAAI")
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PartyOverviewListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameParty) {
            Timber.d("HALLO?")
            binding.txtPartyName.text = item.name
            binding.txtPartyWhen.text =
                SimpleDateFormat("dd/MM/YYYY", Locale.FRENCH).format(item.date)
            binding.txtPartyWhere.text = item.location.coordinates.map { toString() }.toString()
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

class PartyDiffCallback : DiffUtil.ItemCallback<GameParty>() {
    override fun areItemsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameParty, newItem: GameParty): Boolean {
        return oldItem == newItem
    }
}