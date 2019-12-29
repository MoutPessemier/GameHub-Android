package digitized.gamehub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

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
