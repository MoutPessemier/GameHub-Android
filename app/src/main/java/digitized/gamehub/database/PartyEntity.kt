package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.Location
import java.util.*

@Entity(tableName = "parties")
data class PartyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<String>,
    val gameId: String,
    val location: Location
)

fun List<PartyEntity>.asDomainModel(): List<GameParty> {
    return map {
        GameParty(
            it.id,
            it.name,
            it.date,
            it.maxSize,
            it.participants,
            it.gameId,
            it.location
        )
    }
}
