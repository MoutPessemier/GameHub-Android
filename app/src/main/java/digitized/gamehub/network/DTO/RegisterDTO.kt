package digitized.gamehub.network.DTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegisterDTO(var email: String, var firstName: String, var lastName: String) : Parcelable
