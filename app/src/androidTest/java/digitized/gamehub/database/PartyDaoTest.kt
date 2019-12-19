package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.domain.Location
import digitized.gamehub.utilities.*
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class PartyDaoTest {
    private lateinit var database: GameHubDatabase
    private lateinit var partyDao: PartyDao
    private lateinit var userDao: UserDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        partyDao = database.partyDao
        userDao = database.userDao
        partyDao.insertAll(*arrayOf(partyEntity1, partyEntity2, partyEntity3))
        userDao.insertUser(userEntity1)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetParties() {
        val partyList = getValue(partyDao.getParties())
        Assert.assertThat(partyList.size, Matchers.equalTo(3))
        Assert.assertThat(partyList[0].id, Matchers.equalTo(partyEntity1.id))
        Assert.assertThat(partyList[1].id, Matchers.equalTo(partyEntity2.id))
        Assert.assertThat(partyList[2].id, Matchers.equalTo(partyEntity3.id))
    }

    @Test
    fun testGetParty() {
        val party = getValue(partyDao.getParty("5dd0260102611d001e968395"))
        Assert.assertThat(party.id, Matchers.equalTo(partyEntity1.id))
    }

    @Test
    fun testGetJoinedParties() {
        val partyList = getValue(partyDao.getJoinedParties(userEntity1.id))
        Assert.assertThat(partyList[0].id, Matchers.equalTo(partyEntity1.id))
        Assert.assertThat(partyList[2].id, Matchers.equalTo(partyEntity3.id))
        Assert.assertThat(partyList[0].participants[0], Matchers.equalTo(userEntity1.id))
        Assert.assertThat(partyList[2].participants[0], Matchers.equalTo(userEntity1.id))
    }

    @Test
    fun testUpdateGame(){
        partyEntity1.maxSize = 5
        partyDao.update(partyEntity1)
        Assert.assertThat(partyEntity1.maxSize, Matchers.equalTo(5))
    }

    @Test
    fun testJoinParty() {
        partyEntity1.participants = party1.participants.plus("5db8838eaffe445c66076a99")
        partyDao.update(partyEntity1)
        val party = getValue(partyDao.getParty(partyEntity1.id))
        Assert.assertThat(party.participants.size, Matchers.equalTo(2))
        Assert.assertThat(party.participants[0], Matchers.equalTo("5db8838eaffe445c66076a88"))
        Assert.assertThat(party.participants[1], Matchers.equalTo("5db8838eaffe445c66076a99"))
    }

    @Test
    fun testDeclineParty() {
        partyEntity1.declines[0] = "5db8838eaffe445c66076a99"
        partyDao.update(partyEntity1)
        val party = getValue(partyDao.getParty(partyEntity1.id))
        Assert.assertThat(party.participants.size, Matchers.equalTo(1))
        Assert.assertThat(party.declines[0], Matchers.equalTo("5db8838eaffe445c66076a99"))
    }

    @Test
    fun testClearDb() {
        partyDao.clearAll()
        val partyList = getValue(partyDao.getParties())
        Assert.assertThat(partyList.size, Matchers.equalTo(0))
    }
}