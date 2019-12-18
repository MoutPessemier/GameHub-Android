package digitized.gamehub.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import digitized.gamehub.domain.*
import digitized.gamehub.network.DTO.LoginDTO
import digitized.gamehub.network.DTO.PartyInteractionDTO
import digitized.gamehub.network.DTO.RegisterDTO
import digitized.gamehub.repositories.DateAdapter
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

// private const val BASE_URL = "https://game-hub-backend.herokuapp.com/"
private const val BASE_URL = "https://92a97551.ngrok.io/"

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
    /**
     * Gets all the games
     */
    @GET("games")
    fun getAllGames(): Deferred<NetworkGameContainer>

    /**
     * Gets all parties near logged in user
     */
    @GET("getPartiesNearYou")
    fun getPatiesNearYou(
        @Query("distance") distance: Int,
        @Query("lat") lat: Double,
        @Query("long") long: Double,
        @Query("userId") userId: String
    ): Deferred<NetworkPartyContainer>

    /**
     * Gets the user joined parties
     */
    @GET("getJoinedParties")
    fun getJoinedParties(@Query("userId") userId: String): Deferred<NetworkPartyContainer>

    /**
     * Creates a new party
     */
    @POST("createParty")
    fun createParty(@Body party: GameParty): Deferred<NetworkParty>

    /**
     * Joins a party
     */
    @POST("joinParty")
    fun joinParty(@Body partyInteractionDTO: PartyInteractionDTO): Deferred<NetworkParty>

    /**
     * Declines a party
     */
    @POST
    fun declineParty(@Body partyInteractionDTO: PartyInteractionDTO): Deferred<NetworkParty>

    /**
     * Checks if the user already exists
     */
    @GET("doesUserExist")
    fun doesUserExist(@Query("email")email: String): Deferred<Boolean>

    /**
     * Registers the user in my own backend
     */
    @POST("register")
    fun register(@Body registerDTO: RegisterDTO): Deferred<NetworkUserContainer>

    /**
     * Logs the user in
     */
    @POST("login")
    fun login(@Body loginDTO: LoginDTO): Deferred<NetworkUserContainer>

    /**
     * Updates a user's account
     */
    @PUT("updateUser")
    fun updateUser(@Body user: User): Deferred<NetworkUserContainer>
}

object GameHubAPI {
    val service: GameHubAPIService by lazy {
        retrofit.create(GameHubAPIService::class.java)
    }
}