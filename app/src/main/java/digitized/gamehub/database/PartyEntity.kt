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
    var name: String,
    var date: Date,
    var maxSize: Int,
    var participants: Array<String>,
    var declines: Array<String>,
    var gameId: String,
    var location: Location
) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}

fun List<PartyEntity>.asDomainModel(): List<GameParty> {
    return map {
        GameParty(
            it.id,
            it.name,
            it.date,
            it.maxSize,
            it.participants,
            it.declines,
            it.gameId,
            it.location
        )
    }
}
