package digitized.gamehub.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class GameParty(
    @Json(name = "_id") val id: String,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<User>,
    val game: String
    // @Json("location.coordinates") val coordinates: Array<Double>
) : Parcelable {}