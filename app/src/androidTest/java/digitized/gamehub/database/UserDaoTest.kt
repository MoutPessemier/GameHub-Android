package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: GameHubDatabase
    private lateinit var userDao: UserDao

    private var user = UserEntity(
        id = "5db8838eaffe445c66076a88",
        firstName = "test",
        lastName = "user",
        email = "testUser@email.com",
        maxDistance = 10,
        latitude = 51.0538286,
        longitude = 3.7250121
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        userDao = database.userDao
        userDao.insertUser(user)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetUser() {
        val u = getValue(userDao.getUser())
        Assert.assertThat(u, Matchers.equalTo(user))
    }

    @Test
    fun testClearUser() {
        userDao.clear()
        val u = getValue(userDao.getUser())
        Assert.assertNull(u)
    }

    @Test
    fun testUpdate() {
        user.email = "updatedEmail@email.com"
        userDao.update(user)
        val u = getValue(userDao.getUser())
        Assert.assertThat(u.email, Matchers.equalTo("updatedEmail@email.com"))
    }
}