package lastsubmission.capstone.basantaraapps.data.responses

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("data")
	val data: PredictData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class PredictData(

	@field:SerializedName("predicted_class")
	val predictedClass: Int,

	@field:SerializedName("confidence_score")
	val confidenceScore: Double,

	@field:SerializedName("class_name")
	val className: String
)
