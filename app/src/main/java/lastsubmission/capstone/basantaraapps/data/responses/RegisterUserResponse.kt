package lastsubmission.capstone.basantaraapps.data.responses

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
