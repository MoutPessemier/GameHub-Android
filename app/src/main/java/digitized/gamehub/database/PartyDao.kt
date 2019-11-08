package digitized.gamehub.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import digitized.gamehub.domain.GameParty

@Dao
interface PartyDao {
    @Insert
    fun insert(party: PartyEntity)

    @Update
    fun update(party: PartyEntity)

    @Query("select * from Party")
    fun getGames(): List<PartyEntity>?

    @Query("select * from Party where id = :id")
    fun getGame(id:String): PartyEntity?

    @Query("delete from Party where id = :id")
    fun deleteGame(id: String)

    @Query("delete from Party")
    fun clearAll()
}