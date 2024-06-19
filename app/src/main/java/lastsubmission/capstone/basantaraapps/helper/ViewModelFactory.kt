package lastsubmission.capstone.basantaraapps.helper

import android.content.Context
import lastsubmission.capstone.basantaraapps.repository.UserRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lastsubmission.capstone.basantaraapps.dependencies.Injection
import lastsubmission.capstone.basantaraapps.interfaces.login.LoginViewModel
import lastsubmission.capstone.basantaraapps.interfaces.register.RegisterViewModel

class ViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }

            else -> throw  IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}