package lastsubmission.capstone.basantaraapps.dependencies

import android.content.Context
import lastsubmission.capstone.basantaraapps.data.preferences.UserModelPreferences
import lastsubmission.capstone.basantaraapps.data.preferences.dataStore
import lastsubmission.capstone.basantaraapps.data.retrofit.ApiConfig
import lastsubmission.capstone.basantaraapps.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserModelPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preference, apiService)
    }
}