package digitized.gamehub.domain

import android.os.Parcelable
import digitized.gamehub.database.UserEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class User(
    val id: String,
//    val firstName: String,
//    val lastName: String,
//    val telephone: String,
    val email: String,
//    val birthDate: Date,
//    val userRole: UserRole,
//    val password: String,
    val maxDistance: Int,
    val latitude: Double?,
    val longitude: Double?
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