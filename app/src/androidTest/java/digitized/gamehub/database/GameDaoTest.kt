package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.domain.GameType
import digitized.gamehub.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameDaoTest {
    private lateinit var database: GameHubDatabase
    private lateinit var gameDao: GameDao

    private val game1 = GameEntity(
        id = "5dd021b602611d001e968391",
        name = "Mario Party",
        description = "You pick a character and try to get as many stars as you can. You can buy stars with coins that you earn along the way or by winning the round challenges.",
        rules = "Each rond you throw a dice and move the amount of squares you threw. At the end of each round, there is a minigame that can either win or lose you coins. Buy stars with Coins. At the end of 10 rounds, the one with the most stars wins.",
        requirements = "Wii U console.",
        type = GameType.PARTY_GAME,
        visible = true
    )

    private val game2 = GameEntity(
        id = "5db76b7430957f0ef05e73fa",
        name = "Game of Hearts",
        description = "A card game where you aim to get as least points as you can.",
        rules = "All heart cards are worth 1 point, the queen of spade is 13. You need to get the least amount of point or you go all out and aim for 26 points. When this happens, all the others get 26 points. Th first one with 100+ points loses. The two of clubs begins.",
        requirements = "4 players,1 deck of cards",
        type = GameType.CARD_GAME,
        visible = true
    )

    private val game3 = GameEntity(
        id = "5dd0200902611d001e96838e",
        name = "League of Legends",
        description = "LoL is a MOBA type game where you play as a team of 5 players versus 5 other players. The first one to destroy the others homebase (Nexus) wins.",
        rules = "Destory turrets and inhibitors to eventually destory the Nexus and try to win.",
        requirements = "You need to have a LoL account",
        type = GameType.VIDEO_GAME,
        visible = true
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        gameDao = database.gameDao
        gameDao.insertAll(*arrayOf(game1, game2, game3))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetGames() {
        val gameList = getValue(gameDao.getGames())
        Assert.assertThat(gameList.size, Matchers.equalTo(3))
        Assert.assertThat(gameList[0], Matchers.equalTo(game1))
        Assert.assertThat(gameList[1], Matchers.equalTo(game2))
        Assert.assertThat(gameList[2], Matchers.equalTo(game3))
    }

    @Test
    fun testGetGame() {
        val game = getValue(gameDao.getGame("5dd021b602611d001e968391"))
        Assert.assertThat(game, Matchers.equalTo(game1))
    }

    @Test
    fun testClearDb() {
        gameDao.clearAll()
        val gameList = getValue(gameDao.getGames())
        Assert.assertThat(gameList.size, Matchers.equalTo(0))
    }
}