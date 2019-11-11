package digitized.gamehub.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginDTO(var email: String, var password: String) : Parcelable