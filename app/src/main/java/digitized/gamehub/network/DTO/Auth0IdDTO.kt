package digitized.gamehub.network.DTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Auth0IdDTO(
    var sub: String
)
