package digitized.gamehub.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class User(
    @Json(name = "_id") val id: String,
    val firstName: String,
    val lastName: String,
    val telephone: String,
    val email: String,
    val birthDate: Date,
    val userRole: UserRoles,
    val password: String,
    val maxDistance: Int
) : Parcelable {}