package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Query("select * from user")
    fun getUser(): LiveData<UserEntity>

    @Query("delete from user")
    fun clear()
}