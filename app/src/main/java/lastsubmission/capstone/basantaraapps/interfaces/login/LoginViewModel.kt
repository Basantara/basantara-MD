package lastsubmission.capstone.basantaraapps.interfaces.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import lastsubmission.capstone.basantaraapps.data.responses.LoginUserResponse
import lastsubmission.capstone.basantaraapps.repository.UserRepository
import lastsubmission.capstone.basantaraapps.helper.Result

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _loginAction = MediatorLiveData<Result<LoginUserResponse>>()
    val loginAction: LiveData<Result<LoginUserResponse>> = _loginAction

    fun login_optional(email: String, password: String) {
        val liveData = userRepository.login(email, password)
        _loginAction.addSource(liveData) {
            result -> _loginAction.value = result
        }

    }

    fun login(email: String, password: String): LiveData<Result<LoginUserResponse>> {
        return userRepository.login(email, password)
    }
}