package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg games: GameEntity)

    @Query("select * from games")
    fun getGames(): LiveData<List<GameEntity>>

    @Query("select * from games where id = :id")
    fun getGame(id: String): LiveData<GameEntity>

    @Query("delete from games")
    fun clearAll()
}
