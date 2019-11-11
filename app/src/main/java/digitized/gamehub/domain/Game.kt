package digitized.gamehub.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
    val id: String?,
    val name: String,
    val description: String,
    val rules: String,
    val requirements: String,
    val type: GameType
) : Parcelable {}