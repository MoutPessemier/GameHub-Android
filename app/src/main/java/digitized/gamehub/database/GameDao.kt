package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg games: GameEntity)

    @Update
    fun update(game: GameEntity)

    @Query("select * from GameEntity")
    fun getGames(): LiveData<List<GameEntity>>

    @Query("select * from GameEntity where id = :id")
    fun getGame(id:String): LiveData<GameEntity>

    @Query("delete from GameEntity where id = :id")
    fun deleteGame(id: String)

    @Query("delete from GameEntity")
    fun clearAll()
}
