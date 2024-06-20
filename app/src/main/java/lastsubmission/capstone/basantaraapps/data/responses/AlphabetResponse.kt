package lastsubmission.capstone.basantaraapps.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AlphabetResponse(

	@field:SerializedName("AlphabetResponse")
	val alphabetResponse: List<AlphabetResponseItem>
)


data class AlphabetResponseItem(

	@field:SerializedName("descriptionEN")
	val descriptionEN: String,

	@field:SerializedName("imgReal")
	val imgReal: String,

	@field:SerializedName("descriptionID")
	val descriptionID: String,

	@field:SerializedName("imgVector")
	val imgVector: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)
