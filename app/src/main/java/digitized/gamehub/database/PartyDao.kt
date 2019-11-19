package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PartyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg parties: PartyEntity)

    @Update
    fun update(party: PartyEntity)

    @Query("select * from Party")
    fun getParties(): LiveData<List<PartyEntity>>

    @Query("select * from Party where id = :id")
    fun getParty(id:String): LiveData<PartyEntity>

    @Query("select * from Party where participants like '%' || :id || '%'")
    fun getJoinedParties(id: String): LiveData<List<PartyEntity>>

    @Query("delete from Party where id = :id")
    fun deleteParty(id: String)

    @Query("delete from Party")
    fun clearAll()
}
