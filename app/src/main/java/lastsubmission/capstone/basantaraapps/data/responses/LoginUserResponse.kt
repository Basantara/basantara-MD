package lastsubmission.capstone.basantaraapps.data.responses

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Data(

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null
)
