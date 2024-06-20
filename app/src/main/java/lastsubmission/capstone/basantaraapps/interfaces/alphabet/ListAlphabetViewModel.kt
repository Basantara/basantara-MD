package lastsubmission.capstone.basantaraapps.interfaces.alphabet

import android.content.ContentValues.TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponse
import lastsubmission.capstone.basantaraapps.repository.UserRepository
import lastsubmission.capstone.basantaraapps.helper.Result

class ListAlphabetViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _alphabetList = MutableLiveData<List<AlphabetResponseItem>>()
    val alphabetList: LiveData<List<AlphabetResponseItem>> = _alphabetList
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError
    val alphabets: LiveData<Result<AlphabetResponse>> = userRepository.getListAlphabet()

    private val _alphabets = MutableLiveData<Result<AlphabetResponse>>()
    val alphabet: LiveData<Result<AlphabetResponse>> = _alphabets

    fun fetchAlphabets(token: String) {
        viewModelScope.launch {
            _alphabets.value = Result.Loading
            try {
                val response = userRepository.getAlphabetsCuy("Bearer $token")
                _alphabets.value = Result.Success(response)
            } catch (e: Exception) {
                _alphabets.value = Result.Error(e.message.toString())
            }
        }
    }


}