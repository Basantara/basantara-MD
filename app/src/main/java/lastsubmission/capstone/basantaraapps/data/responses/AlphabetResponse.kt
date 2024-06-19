package lastsubmission.capstone.basantaraapps.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AlphabetResponse(

	@field:SerializedName("AlphabetResponse")
	val alphabetResponse: List<AlphabetResponseItem> = emptyList()
)


data class AlphabetResponseItem(

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
