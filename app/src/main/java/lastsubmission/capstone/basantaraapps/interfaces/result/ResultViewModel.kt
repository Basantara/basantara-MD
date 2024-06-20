package lastsubmission.capstone.basantaraapps.interfaces.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import lastsubmission.capstone.basantaraapps.data.responses.PredictResponse
import lastsubmission.capstone.basantaraapps.data.retrofit.ML.ApiConfigML
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel : ViewModel()  {
    private val _predictResponse = MutableLiveData<PredictResponse>()
    val predictResponse: LiveData<PredictResponse> = _predictResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun predict(imageFile: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfigML.getApiService().predict(imageFile)
        client.enqueue(object : Callback<PredictResponse> {
            override fun onResponse(call: Call<PredictResponse>, response: Response<PredictResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _predictResponse.value = response.body()
                } else {
                    _error.value = response.message()
                }
            }

            override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                _isLoading.value = false
                _error.value = t.message
            }
        })
    }

}