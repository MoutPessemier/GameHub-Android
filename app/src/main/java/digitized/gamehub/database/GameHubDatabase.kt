package digitized.gamehub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GameEntity::class, PartyEntity::class, UserEntity::class], version = 8, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameHubDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val partyDao: PartyDao
    abstract val userDao: UserDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: GameHubDatabase

        /**
         * Gives a singleton object of the database back
         * This method is synchronized that way only one object will ever be created
         */
        fun getInstance(context: Context): GameHubDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameHubDatabase::class.java,
                        "gamehub_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }
    }
}
