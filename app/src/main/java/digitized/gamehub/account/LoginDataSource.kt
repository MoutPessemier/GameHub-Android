package digitized.gamehub.account

import digitized.gamehub.domain.User
import digitized.gamehub.domain.UserRole
import java.io.IOException
import java.util.*

class LoginDataSource {
    fun login(email: String, password: String): Result<User> {
        try {
            val user = User(
                "1",
                "Mout",
                "Pessemier",
                "0478112233",
                "moutpessemier@hotmail.com",
                Date(),
                UserRole.ADMIN,
                "pass",
                10
            )
            return Result.Success(user)
        } catch (e: Exception) {
            return Result.Error(
                IOException(
                    "Error logging in",
                    e
                )
            )
        }

    }

    fun logout() {

    }

    fun register() {

    }
}
