package digitized.gamehub.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import digitized.gamehub.database.UserEntity
import digitized.gamehub.domain.User
import digitized.gamehub.domain.UserRole
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val user: NetworkUser)

@JsonClass(generateAdapter = true)
data class NetworkUser(
    @Json(name = "_id") val id: String,
    val firstName: String,
    val lastName: String,
    val telephone: String,
    val email: String,
    val birthDate: Date,
    val userRole: UserRole,
    val password: String,
    val maxDistance: Int
)

fun NetworkUserContainer.asDomainModel(): User {
    return User(
        user.id,
//        user.firstName,
//        user.lastName,
//        user.telephone,
        user.email,
//        user.birthDate,
//        user.userRole,
//        user.password,
        user.maxDistance,
        null,
        null
    )
}

fun NetworkUserContainer.asDatabaseModel(): UserEntity {
    return UserEntity(
        user.id,
//        user.firstName,
//        user.lastName,
//        user.telephone,
        user.email,
//        user.birthDate,
//        user.userRole,
//        user.password,
        user.maxDistance,
        null,
        null
    )
}

