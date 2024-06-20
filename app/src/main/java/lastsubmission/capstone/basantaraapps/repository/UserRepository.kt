package lastsubmission.capstone.basantaraapps.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import lastsubmission.capstone.basantaraapps.data.preferences.UserModel
import lastsubmission.capstone.basantaraapps.data.preferences.UserModelPreferences
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponse
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.data.retrofit.ApiService
import lastsubmission.capstone.basantaraapps.helper.Result
import kotlinx.coroutines.flow.Flow
import lastsubmission.capstone.basantaraapps.data.responses.LoginUserResponse
import lastsubmission.capstone.basantaraapps.data.responses.RegisterUserRequest
import lastsubmission.capstone.basantaraapps.data.responses.RegisterUserResponse
import retrofit2.HttpException
import java.io.IOException

class UserRepository private constructor( private val userModelPreferences: UserModelPreferences, private val apiService: ApiService) {

    fun register(request: RegisterUserRequest): LiveData<Result<RegisterUserResponse>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.register(request)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginUserResponse>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.login(ApiService.LoginRequestBody(email, password)).execute()
            if (response.isSuccessful) {
                val loginResponse = response.body()!!
                val token = loginResponse.token ?: ""
                userModelPreferences.saveSession(UserModel(email, token, true))
                emit(Result.Success(loginResponse))
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    fun getListAlphabet(): LiveData<Result<AlphabetResponse>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.getAlphabets().execute()
            if (response.isSuccessful) {
                val alphabetResponse = response.body()
                if (alphabetResponse != null) {
                    emit(Result.Success(alphabetResponse))
                } else {
                    emit(Result.Error("Response body is null"))
                }
            } else {
                emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
   suspend fun getAlphabetsCuy(token: String): AlphabetResponse {
       Log.d("UserRepository", "Fetching alphabets with token: $token")
        return apiService.getAlphabets(token)
   }

    fun logins(email: String, password: String): LiveData<Result<LoginUserResponse>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val jsonObject = JsonObject().apply {
                addProperty("email", email)
                addProperty("password", password)
            }

            val response = apiService.logins(jsonObject)

            emit(Result.Success(response))
        } catch (e: IOException) {
            emit(Result.Error("Network error: ${e.message}"))
        } catch (e: HttpException) {
            emit(Result.Error("API error: ${e.message}"))
        } catch (e: Exception) {
            emit(Result.Error("Unexpected error: ${e.message}"))
        }
    }



    suspend fun logout() {
        userModelPreferences.logout()
    }

    fun getSession(): Flow<UserModel> {
        return userModelPreferences.getSession()
    }

    companion object {
        @Volatile
        private var instance : UserRepository? = null

        fun getInstance(userModelPreferences: UserModelPreferences, apiService: ApiService): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(userModelPreferences, apiService)
        }.also { instance = it }
    }


}