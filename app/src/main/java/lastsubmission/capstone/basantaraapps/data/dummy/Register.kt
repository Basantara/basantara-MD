package lastsubmission.capstone.basantaraapps.data.dummy

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Register(
    val name: String,
    val email: String,
    val password: String

): Parcelable
