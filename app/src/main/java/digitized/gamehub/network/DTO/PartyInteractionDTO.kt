package digitized.gamehub.network.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PartyInteractionDTO(private var partyId: String, private var userId: String) : Parcelable