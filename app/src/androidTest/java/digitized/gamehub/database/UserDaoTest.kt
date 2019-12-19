package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.utilities.getValue
import digitized.gamehub.utilities.userEntity1
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: GameHubDatabase
    private lateinit var userDao: UserDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        userDao = database.userDao
        userDao.insertUser(userEntity1)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetUser() {
        val u = getValue(userDao.getUser())
        Assert.assertThat(u, Matchers.equalTo(userEntity1))
    }

    @Test
    fun testClearUser() {
        userDao.clear()
        val u = getValue(userDao.getUser())
        Assert.assertNull(u)
    }

    @Test
    fun testUpdate() {
        userEntity1.email = "updatedEmail@email.com"
        userDao.update(userEntity1)
        val u = getValue(userDao.getUser())
        Assert.assertThat(u.email, Matchers.equalTo("updatedEmail@email.com"))
    }
}
