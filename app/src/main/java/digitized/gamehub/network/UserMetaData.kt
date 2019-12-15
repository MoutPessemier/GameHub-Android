package digitized.gamehub.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class UserMetaData(
    var fname: String,
    var lname: String
)