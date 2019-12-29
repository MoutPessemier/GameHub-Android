package digitized.gamehub.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import digitized.gamehub.network.DTO.AccessTokenDTO
import digitized.gamehub.network.DTO.Auth0IdDTO
import digitized.gamehub.network.DTO.Auth0UserDTO
import digitized.gamehub.repositories.DateAdapter
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "https://gamehub.eu.auth0.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(DateAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface Auth0ApiService {
    /**
     * Gets the user info from Auth0
     */
    @GET("userinfo")
    fun getAccount(@Header("Authorization") AccessTokenBearer: String): Deferred<Auth0IdDTO>

    /**
     * gets the user (metadata) from Auth0
     */
    @GET("/api/v2/users/{id}")
    fun getUser(
        @Header("Authorization") accessJwt: String,
        @Path("id") id: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): Deferred<Auth0UserDTO>

    /**
     * Gets the access token for the user
     */
    @POST("oauth/token")
    fun getAccessToken(
        @Header("Content-Type") contentType: String,
        @Body body: AccessTokenRequest
    ): Deferred<AccessTokenDTO>
}

object Auth0API {
    val service: Auth0ApiService by lazy {
        retrofit.create(Auth0ApiService::class.java)
    }
}
