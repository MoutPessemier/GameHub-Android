package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.User

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    var id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var maxDistance: Int,
    var latitude: Double?,
    var longitude: Double?
)

fun UserEntity.asDomainModel(): User {
    return User(
        id, firstName, lastName, email, maxDistance, latitude, longitude
    )
}
