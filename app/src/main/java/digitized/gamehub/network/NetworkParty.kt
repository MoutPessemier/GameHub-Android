package digitized.gamehub.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import digitized.gamehub.database.PartyEntity
import digitized.gamehub.domain.GameParty
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
    val gameId: String
)

fun NetworkPartyContainer.asDomainModel(): List<GameParty> {
    return parties.map {
        GameParty(
            it.id,
            it.name,
            it.date,
            it.maxSize,
            it.participants,
            it.gameId
        )
    }
}

fun NetworkPartyContainer.asDatabaseModel(): Array<PartyEntity> {
    return parties.map {
        PartyEntity(
            it.id,
            it.name,
            it.date,
            it.maxSize,
            it.participants,
            it.gameId
        )
    }.toTypedArray()
}