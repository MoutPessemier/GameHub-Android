package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.Game
import digitized.gamehub.domain.GameType

@Entity(tableName = "Game")
data class GameEntity(
    @PrimaryKey
    var id: String,
    var name: String,
    var description: String,
    var rules: String,
    var requirements: String,
    var type: GameType
)

fun List<GameEntity>.asDomainModel(): List<Game> {
    return map {
        Game(
            it.id,
            it.name,
            it.description,
            it.rules,
            it.requirements,
            it.type
        )
    }
}