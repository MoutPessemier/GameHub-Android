package digitized.gamehub.account

import digitized.gamehub.domain.User

data class LoginResult(
    val success: User? = null,
    val error: Int? = null
)
