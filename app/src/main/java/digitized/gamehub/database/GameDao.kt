package digitized.gamehub.database

import androidx.room.*
import digitized.gamehub.domain.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(game: GameEntity)

    @Update
    fun update(game: GameEntity)

    @Query("select * from Game")
    fun getGames(): List<GameEntity>?

    @Query("select * from Game where id = :id")
    fun getGame(id:String): GameEntity?

    @Query("delete from Game where id = :id")
    fun deleteGame(id: String)

    @Query("delete from Game")
    fun clearAll()
}