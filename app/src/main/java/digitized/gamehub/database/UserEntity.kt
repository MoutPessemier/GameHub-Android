package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.UserRole
import java.util.*

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String,
    val telephone: String,
    val email: String,
    val birthDate: Date,
    val userRole: UserRole,
    val password: String,
    val maxDistance: Int
//    ,
//    val lat: Double?,
//    val long: Double?
)