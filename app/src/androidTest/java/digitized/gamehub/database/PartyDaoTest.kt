package digitized.gamehub.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import digitized.gamehub.domain.Location
import digitized.gamehub.utilities.getValue
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

    private val party1 = PartyEntity(
        id = "5dd0260102611d001e968395",
        name = "Master LoL Scrimms",
        date = SimpleDateFormat("yyyy-MM-dd").parse("2020-2-29")!!,
        maxSize = 5,
        participants = arrayOf("5db8838eaffe445c66076a88"),
        declines = arrayOf(""),
        gameId = "5dd0200902611d001e96838e",
        location = Location(
            type = "Point",
            coordinates = doubleArrayOf(51.0416, 3.71426)
        )
    )

    private val party2 = PartyEntity(
        id = "5dd0272b02611d001e968398",
        name = "Mario 'n Chill",
        date = SimpleDateFormat("yyyy-MM-dd").parse("2020-1-25")!!,
        maxSize = 4,
        participants = arrayOf("5db8838eaffe445c66076a86"),
        declines = arrayOf(""),
        gameId = "5dd021b602611d001e968391",
        location = Location(
            type = "Point",
            coordinates = doubleArrayOf(51.0572, 3.57238)
        )
    )

    private val party3 = PartyEntity(
        id = "5dd0272b02611d001e968399",
        name = "Mario 'n Chill2",
        date = SimpleDateFormat("yyyy-MM-dd").parse("2020-1-25")!!,
        maxSize = 4,
        participants = arrayOf("5db8838eaffe445c66076a88"),
        declines = arrayOf(""),
        gameId = "5dd021b602611d001e968391",
        location = Location(
            type = "Point",
            coordinates = doubleArrayOf(51.0572, 3.57238)
        )
    )

    private val user = UserEntity(
        id = "5db8838eaffe445c66076a88",
        email = "testUser@email.com",
        maxDistance = 10
        //lat = 51.0538286,
        //long = 3.7250121
    )

    private val user2 = UserEntity(
        id = "5db8838eaffe445c66076a99",
        email = "testUser2@email.com",
        maxDistance = 25
        //lat = 51.0538286,
        //long = 3.7250121
    )

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, GameHubDatabase::class.java).build()
        partyDao = database.partyDao
        userDao = database.userDao
        partyDao.insertAll(*arrayOf(party1, party2, party3))
        userDao.insertUser(user)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetParties() {
        val partyList = getValue(partyDao.getParties())
        Assert.assertThat(partyList.size, Matchers.equalTo(3))
        // appart slaagt alles maar als ik volledig object check faalt die? --> textcompare tussen the expected and but was is exact dezelfde?
        Assert.assertThat(partyList[0], Matchers.equalTo(party1))
        Assert.assertThat(partyList[1], Matchers.equalTo(party2))
        Assert.assertThat(partyList[2], Matchers.equalTo(party3))
    }

    @Test
    fun testGetParty() {
        val party = getValue(partyDao.getParty("5dd0260102611d001e968395"))
        Assert.assertThat(party, Matchers.equalTo(party1))
    }


    //same problem here
    @Test
    fun testGetJoinedParties() {
        val partyList = getValue(partyDao.getJoinedParties(user.id))
        Assert.assertThat(partyList[0], Matchers.equalTo(party1))
        Assert.assertThat(partyList[2], Matchers.equalTo(party3))
        Assert.assertThat(partyList[0].participants[0], Matchers.equalTo(user.id))
        Assert.assertThat(partyList[2].participants[0], Matchers.equalTo(user.id))
    }

    @Test
    fun testUpdateGame(){
        party1.maxSize = 5
        partyDao.update(party1)
        Assert.assertThat(party1.maxSize, Matchers.equalTo(5))
    }

    @Test
    fun testJoinParty() {
        party1.participants.plus("5db8838eaffe445c66076a99")
        partyDao.update(party1)
        val party = getValue(partyDao.getParty(party1.id))
        Assert.assertThat(party.participants.size, Matchers.equalTo(2))
        Assert.assertThat(party.participants[0], Matchers.equalTo("5db8838eaffe445c66076a88"))
        Assert.assertThat(party.participants[1], Matchers.equalTo("5db8838eaffe445c66076a99"))
    }

    @Test
    fun testDeclineParty() {
        party1.declines.plus("5db8838eaffe445c66076a99")
        partyDao.update(party1)
        val party = getValue(partyDao.getParty(party1.id))
        Assert.assertThat(party.participants.size, Matchers.equalTo(1))
        Assert.assertThat(party.participants[0], Matchers.equalTo("5db8838eaffe445c66076a99"))
    }

    @Test
    fun testClearDb() {
        partyDao.clearAll()
        val partyList = getValue(partyDao.getParties())
        Assert.assertThat(partyList.size, Matchers.equalTo(0))
    }
}