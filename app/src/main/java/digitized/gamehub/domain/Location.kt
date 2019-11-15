package digitized.gamehub.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val type: String,
    val coordinates: DoubleArray
): Parcelable