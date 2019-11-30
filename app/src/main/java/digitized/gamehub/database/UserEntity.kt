package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.User
import digitized.gamehub.domain.UserRole
import java.util.*

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    var id: String,
//    val firstName: String,
//    val lastName: String,
//    val telephone: String,
    var email: String,
//    val birthDate: Date,
//    val userRole: UserRole,
//    val password: String,
    var maxDistance: Int,
    var latitude: Double?,
    var longitude: Double?
)

fun UserEntity.asDomainModel(): User {
    return User(
        id,
        email,
        maxDistance,
        latitude,
        longitude
    )
}