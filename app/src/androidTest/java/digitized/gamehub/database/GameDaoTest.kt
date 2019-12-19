package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.utilities.gameEntity1
import digitized.gamehub.utilities.gameEntity2
import digitized.gamehub.utilities.gameEntity3
import digitized.gamehub.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameDaoTest {
    private lateinit var database: GameHubDatabase
    private lateinit var gameDao: GameDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        gameDao = database.gameDao
        gameDao.insertAll(*arrayOf(gameEntity1, gameEntity2, gameEntity3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetGames() {
        val gameList = getValue(gameDao.getGames())
        Assert.assertThat(gameList.size, Matchers.equalTo(3))
        Assert.assertThat(gameList[0], Matchers.equalTo(gameEntity1))
        Assert.assertThat(gameList[1], Matchers.equalTo(gameEntity2))
        Assert.assertThat(gameList[2], Matchers.equalTo(gameEntity3))
    }

    @Test
    fun testGetGame() {
        val game = getValue(gameDao.getGame("5dd021b602611d001e968391"))
        Assert.assertThat(game, Matchers.equalTo(gameEntity1))
    }

    @Test
    fun testClearDb() {
        gameDao.clearAll()
        val gameList = getValue(gameDao.getGames())
        Assert.assertThat(gameList.size, Matchers.equalTo(0))
    }
}
