package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PartyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg parties: PartyEntity)

    @Update
    fun update(party: PartyEntity)

    @Query("select * from parties")
    fun getParties(): LiveData<List<PartyEntity>>

    @Query("select * from parties where id = :id")
    fun getParty(id: String): LiveData<PartyEntity>

    @Query("select * from parties where participants like '%' || :id || '%'")
    fun getJoinedParties(id: String): LiveData<List<PartyEntity>>

    @Query("select * from parties where participants not like '%' || :id || '%' and declines not like '%' || :id || '%'")
    fun getNewParties(id: String): LiveData<List<PartyEntity>>

    @Query("delete from parties")
    fun clearAll()
}
