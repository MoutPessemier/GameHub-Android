package digitized.gamehub.partyInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import digitized.gamehub.domain.GameParty

class PartyInfoViewModelFactory(private val party: GameParty): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GamePartyInfoViewModel::class.java)) {
            return GamePartyInfoViewModel(party) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
