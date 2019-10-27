package digitized.gamehub.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

private const val BASE_URL = "http://localhost:9753/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GameHubAPIService {
    // Game
    @GET("games")
    fun getAllGames(): Call<String>

    @POST("makeGame")
    fun makeGame(/*name, description, rules, requirements, gametype*/): Call<String>

    @PUT("updateGame")
    fun updateGame(/*id, name, description, rules, requirements, gametype*/): Call<String>

    @DELETE("deleteGame")
    fun deleteGame(/*id*/): Call<String>

    // Party
    @GET("getPartiesNearYou")
    fun getPatiesNearYou(/*[coordinates], max distance away*/): Call<String>

    @POST("createParty")
    fun makeParty(/*name, date, maxSize, [participants], [coordinates]*/): Call<String>

    @PUT("updateParty")
    fun updateParty(/*id, name, date, maxSize, [participants], [coordinates]*/): Call<String>

    @DELETE("deleteParty")
    fun deleteParty(/*id*/): Call<String>

    // User

}

object GameHubAPI{
    val service: GameHubAPIService by lazy {
        retrofit.create(GameHubAPIService::class.java)
    }
}