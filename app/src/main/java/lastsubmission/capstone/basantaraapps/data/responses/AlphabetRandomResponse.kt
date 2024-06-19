package lastsubmission.capstone.basantaraapps.data.responses

import com.google.gson.annotations.SerializedName

data class AlphabetRandomResponse(

	@field:SerializedName("AlphabetRandomResponse")
	val alphabetRandomResponse: List<AlphabetRandomResponseItem?>? = null
)

data class AlphabetRandomResponseItem(

	@field:SerializedName("descriptionEN")
	val descriptionEN: String? = null,

	@field:SerializedName("imgReal")
	val imgReal: String? = null,

	@field:SerializedName("descriptionID")
	val descriptionID: String? = null,

	@field:SerializedName("imgVector")
	val imgVector: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
