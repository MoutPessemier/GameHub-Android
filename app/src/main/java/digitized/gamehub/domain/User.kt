package digitized.gamehub.domain

import android.os.Parcelable
import digitized.gamehub.database.UserEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class User(
    var id: String,
    var email: String,
    var maxDistance: Int,
    var latitude: Double?,
    var longitude: Double?
) : Parcelable

fun User.asDatabaseModel(): UserEntity {
    return UserEntity(
        id,
        email,
        maxDistance,
        latitude,
        longitude
    )
}