package digitized.gamehub.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import digitized.gamehub.domain.*
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

private const val BASE_URL = "https://game-hub-backend.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface GameHubAPIService {
    // Game
    @GET("games")
    fun getAllGames(): Deferred<NetworkGameContainer>

    @GET("gameById")
    fun getGameById(@Query("id") id: String): Deferred<NetworkGame>

    @POST("createGame")
    fun createGame(@Body game: Game): Deferred<NetworkGame>

    @PUT("updateGame")
    fun updateGame(@Body game: Game): Deferred<NetworkGame>

    @DELETE("deleteGame")
    fun deleteGame(@Body id: String): Deferred<String>

    // Party
    @GET("getPartiesNearYou")
    fun getPatiesNearYou(
        @Query("distance") distance: Int,
        @Query("lat") lat: Double,
        @Query("long") long: Double
    ): Deferred<NetworkPartyContainer>

    @POST("createParty")
    fun createParty(@Body party: GameParty): Deferred<NetworkParty>

    @PUT("updateParty")
    fun updateParty(@Body party: GameParty): Deferred<NetworkParty>

    @DELETE("deleteParty")
    fun deleteParty(@Body id: String): Deferred<String>

    @POST("joinParty")
    fun joinParty(partyId: String, userId: String): Deferred<NetworkParty>

    @POST
    fun declineParty(partyId: String, userId: String): Deferred<NetworkParty>

    // User
    @POST("login")
    fun login(email: String, password: String): Deferred<User>

    @POST("register")
    fun register(@Body user: User): Deferred<User>

    @PUT("updateUser")
    fun updateUser(@Body user: User): Deferred<User>

    @DELETE("deleteUser")
    fun deleteUser(@Body id: String): Deferred<String>

}

object GameHubAPI {
    val service: GameHubAPIService by lazy {
        retrofit.create(GameHubAPIService::class.java)
    }
}