package digitized.gamehub.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import digitized.gamehub.domain.*
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.network.DTO.RegisterDTO
import digitized.gamehub.repositories.DateAdapter
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://game-hub-backend.herokuapp.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter())
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

    // Party
    @GET("getPartiesNearYou")
    fun getPatiesNearYou(
        @Query("distance") distance: Int,
        @Query("lat") lat: Double,
        @Query("long") long: Double,
        @Query("userId") userId: String
    ): Deferred<NetworkPartyContainer>

    @GET("getJoinedParties")
    fun getJoinedParties(@Query("userId") userId: String): Deferred<NetworkPartyContainer>

    @POST("createParty")
    fun createParty(@Body party: GameParty): Deferred<NetworkParty>

    @POST("joinParty")
    fun joinParty(@Body partyInteractionDTO: PartyInteractionDTO): Deferred<NetworkParty>

    @POST
    fun declineParty(@Body partyInteractionDTO: PartyInteractionDTO): Deferred<NetworkParty>

    // User
    @GET("doesUserExist")
    fun doesUserExist(): Deferred<Boolean>

    @POST("register")
    fun register(@Body registerDTO: RegisterDTO): Deferred<NetworkUserContainer>

    @POST("login")
    fun login(@Body email: String): Deferred<NetworkUserContainer>

    @PUT("updateUser")
    fun updateUser(@Body user: User): Deferred<NetworkUserContainer>

    @DELETE("deleteUser")
    fun deleteUser(@Body id: String): Deferred<String>

}

object GameHubAPI {
    val service: GameHubAPIService by lazy {
        retrofit.create(GameHubAPIService::class.java)
    }
}