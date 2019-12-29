package digitized.gamehub.network.DTO

import com.squareup.moshi.JsonClass
import digitized.gamehub.domain.User
import digitized.gamehub.network.UserMetaData

@JsonClass(generateAdapter = true)
data class Auth0UserDTO(
    var email: String,
    var user_metadata: UserMetaData,
    var user_id: String
)

fun Auth0UserDTO.asDomainModel(): User {
    return User(
        "",
        user_metadata.fname,
        user_metadata.lname,
        email,
        10,
        0.0, 0.0

    )
}
