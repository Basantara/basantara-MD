package lastsubmission.capstone.basantaraapps.data.retrofit

import lastsubmission.capstone.basantaraapps.data.responses.AlphabetRandomResponse
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponse
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.data.responses.LoginUserResponse
import lastsubmission.capstone.basantaraapps.data.responses.RegisterUserRequest
import lastsubmission.capstone.basantaraapps.data.responses.RegisterUserResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.Body

interface ApiService  {


    data class LoginRequestBody(
        val email: String,
        val password: String
    )

    @FormUrlEncoded
    @POST("api/users/login")
    suspend fun login(
        @Field("email")
        email: String,

        @Field("password")
        password: String
    ) : LoginUserResponse

    @POST("api/users/login")
    fun login(@Body requestBody: LoginRequestBody): Call<LoginUserResponse>


    @FormUrlEncoded
    @POST("api/users/register")
    suspend fun register_optional(

        @Field("username")
        username: String,

        @Field("email")
        email: String,

        @Field("password")
        password: String
    ): RegisterUserResponse

    @FormUrlEncoded
    @POST("api/users/register")
    suspend fun register(
        @Body request: RegisterUserRequest
    ): RegisterUserResponse

    @GET("api/alphabets")
    suspend fun getAlphabet() : AlphabetResponse


    @GET("api/alphabets")
    fun getAlphabets(): Call<AlphabetResponse>

    @GET("api/alphabets")
    suspend fun getAlphabetOptional(): List<AlphabetResponseItem>

    @GET("api/alphabets/random")
    suspend fun getAlphabetRandom(): AlphabetRandomResponse




}