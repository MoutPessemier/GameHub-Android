package digitized.gamehub.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GameParty(
    val id: String?,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<String>,
    val gameId: String
    //,@Json("location.coordinates") val coordinates: DoubleArray
) : Parcelable
