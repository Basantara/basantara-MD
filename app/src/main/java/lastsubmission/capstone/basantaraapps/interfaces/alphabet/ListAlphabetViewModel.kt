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
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponse

class ListAlphabetViewModel: ViewModel() {
    private val _alphabetList = MutableLiveData<List<AlphabetResponseItem>>()
    val alphabetList: LiveData<List<AlphabetResponseItem>> = _alphabetList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        fetchAlphabets()
    }

    private fun fetchAlphabets() {
        _isLoading.value = true
        _isError.value = false
        val client = ApiConfig.getApiService().getAlphabets()
        client.enqueue(object : Callback<AlphabetResponse> {
            override fun onResponse(call: Call<AlphabetResponse>, response: Response<AlphabetResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _alphabetList.value = response.body()?.alphabetResponse
                } else {
                    _isError.value = true
                    Log.e(TAG, "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AlphabetResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.e(TAG, "Failure: ${t.message}")
            }
        })
    }
}