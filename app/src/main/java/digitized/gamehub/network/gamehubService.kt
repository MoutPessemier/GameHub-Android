package digitized.gamehub.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import digitized.gamehub.domain.*
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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
    fun getAllGames(): Deferred<List<Game>>

    @POST("createGame")
    fun createGame(
        name: String,
        description: String,
        rules: String,
        requirements: String,
        type: GameType
    ): Deferred<Game>

    @PUT("updateGame")
    fun updateGame(
        id: String,
        name: String,
        description: String,
        rules: String,
        requirements: String,
        type: GameType
    ): Deferred<Game>

    @DELETE("deleteGame")
    fun deleteGame(id: String): Deferred<String>

    // Party
    @GET("getPartiesNearYou")
    fun getPatiesNearYou(distance: Int, coordinates: Array<Double>): Deferred<List<GameParty>>

    @POST("createParty")
    fun createParty(
        name: String,
        date: Date,
        maxSize: Int,
        participants: Array<String>,
        coordinates: Array<Double>,
        game: String
    ): Deferred<GameParty>

    @PUT("updateParty")
    fun updateParty(
        id: String,
        name: String,
        date: Date,
        maxSize: Int,
        participants: Array<String>,
        coordinates: Array<Double>,
        game: String
    ): Deferred<GameParty>

    @DELETE("deleteParty")
    fun deleteParty(id: String): Deferred<String>

    @POST("joinParty")
    fun joinParty(partyId: String, userId: String): Deferred<GameParty>

    // User
    @POST("login")
    fun login(email: String, password: String): Deferred<User>

    @POST("register")
    fun register(
        firstName: String,
        lastName: String,
        telephone: String,
        email: String,
        birthDate: Date,
        userRole: UserRole,
        password: String,
        maxDistance: Int
    ): Deferred<User>

    @PUT("updateUser")
    fun updateUser(
        id: String,
        firstName: String,
        lastName: String,
        telephone: String,
        email: String,
        birthDate: Date,
        userRole: UserRole,
        password: String,
        maxDistance: Int
    ): Deferred<User>

    @DELETE("deleteUser")
    fun deleteUser(id: String): Deferred<String>

}

object GameHubAPI {
    val service: GameHubAPIService by lazy {
        retrofit.create(GameHubAPIService::class.java)
    }
}