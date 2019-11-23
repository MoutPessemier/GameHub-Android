package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val rules: String,
    val requirements: String,
    val type: GameType,
    val visible: Boolean
)

fun List<GameEntity>.asDomainModel(): List<Game> {
    return map {
        Game(
            it.id,
            it.name,
            it.description,
            it.rules,
            it.requirements,
            it.type,
            it.visible
        )
    }
}
