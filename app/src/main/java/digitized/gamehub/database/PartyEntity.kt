package digitized.gamehub.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Party")
data class PartyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val date: Date,
    val maxSize: Int,
    val participants: Array<String>,
    val gameId: String
) {

}