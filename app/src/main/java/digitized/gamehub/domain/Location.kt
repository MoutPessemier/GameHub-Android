package digitized.gamehub.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val type: String,
    val coordinates: DoubleArray
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        return this.coordinates[0] == (other as Location).coordinates[0] && this.coordinates[1] == (other as Location).coordinates[1]
    }
}
