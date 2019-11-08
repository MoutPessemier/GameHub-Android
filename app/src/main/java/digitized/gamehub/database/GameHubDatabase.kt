package digitized.gamehub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GameEntity::class, PartyEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameHubDatabase : RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val partyDao: PartyDao

    companion object {
        @Volatile
        private var INSTANCE: GameHubDatabase? = null

        fun getInstance(context: Context): GameHubDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameHubDatabase::class.java,
                        "gamehub_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}