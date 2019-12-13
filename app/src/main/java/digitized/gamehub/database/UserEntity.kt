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
    var firstName: String,
    var lastName: String,
    var email: String,
    var maxDistance: Int,
    var latitude: Double?,
    var longitude: Double?
)