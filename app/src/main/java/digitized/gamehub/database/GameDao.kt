package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.*
import digitized.gamehub.domain.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg games: GameEntity)

    @Update
    fun update(game: GameEntity)

    @Query("select * from Game")
    fun getGames(): LiveData<List<GameEntity>>

    @Query("select * from Game where id = :id")
    fun getGame(id:String): LiveData<GameEntity>

    @Query("delete from Game where id = :id")
    fun deleteGame(id: String)

    @Query("delete from Game")
    fun clearAll()
}