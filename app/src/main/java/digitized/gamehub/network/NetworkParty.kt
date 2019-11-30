package digitized.gamehub.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import digitized.gamehub.database.PartyEntity
import digitized.gamehub.domain.GameParty
import digitized.gamehub.domain.Location
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkPartyContainer(val parties: List<NetworkParty>)

@JsonClass(generateAdapter = true)
data class NetworkParty(
    @Json(name = "_id") val id: String,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<String>,
    val declines: Array<String>,
    val gameId: String,
    val location: Location
)

fun NetworkPartyContainer.asDatabaseModel(): Array<PartyEntity> {
    return parties.map {
        PartyEntity(
            it.id,
            it.name,
            it.date,
            it.maxSize,
            it.participants,
            it.declines,
            it.gameId,
            it.location
        )
    }.toTypedArray()
}

fun NetworkParty.asDatabaseModel(): PartyEntity {
    return PartyEntity(
        id,
        name,
        date,
        maxSize,
        participants,
        declines,
        gameId,
        location
    )
}