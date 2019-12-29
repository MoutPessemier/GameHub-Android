package digitized.gamehub.cardStack

import androidx.recyclerview.widget.DiffUtil
import digitized.gamehub.domain.GameParty

class PartyDiffCallback(
    private val old: List<GameParty>,
    private val new: List<GameParty>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}
