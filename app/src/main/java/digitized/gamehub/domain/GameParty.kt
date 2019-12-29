package digitized.gamehub.domain

import android.os.Parcelable
import digitized.gamehub.database.PartyEntity
import java.util.Date
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameParty(
    val id: String?,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<String>,
    val declines: Array<String>,
    val gameId: String,
    val location: Location
) : Parcelable

fun GameParty.asDatabaseModel(): PartyEntity {
    return PartyEntity(id!!, name, date, maxSize, participants, declines, gameId, location)
}
