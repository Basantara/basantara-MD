package lastsubmission.capstone.basantaraapps.data.retrofit.ML

import lastsubmission.capstone.basantaraapps.data.responses.PredictResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import okhttp3.MultipartBody
import retrofit2.http.Part
import retrofit2.Call

interface  ApiServiceML {

    @Multipart
    @POST("predict")
    fun predict(
        @Part imageFile: MultipartBody.Part
    ): Call<PredictResponse>
}