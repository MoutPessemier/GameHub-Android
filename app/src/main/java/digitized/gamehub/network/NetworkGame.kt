package digitized.gamehub.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import digitized.gamehub.database.GameEntity
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType

@JsonClass(generateAdapter = true)
data class NetworkGameContainer(val games: List<NetworkGame>)


@JsonClass(generateAdapter = true)
data class NetworkGame(
    @Json(name = "_id") val id: String,
    val name: String,
    val description: String,
    val rules: String,
    val requirements: String,
    val type: GameType,
    val visible: Boolean
)

fun NetworkGameContainer.asDatabaseModel(): Array<GameEntity> {
    return games.map {
        GameEntity(
            it.id,
            it.name,
            it.description,
            it.rules,
            it.requirements,
            it.type,
            it.visible
        )
    }.toTypedArray()
}

