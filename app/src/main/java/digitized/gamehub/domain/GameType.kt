package digitized.gamehub.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class GameType : Parcelable {
    @Json(name = "BOARD_GAME")
    BOARD_GAME,
    @Json(name = "CARD_GAME")
    CARD_GAME,
    @Json(name = "VIDEO_GAME")
    VIDEO_GAME,
    @Json(name = "DnD")
    DnD,
    @Json(name = "PARTY_GAME")
    PARTY_GAME,
    @Json(name = "FAMILY_GAME")
    FAMILY_GAME,
    @Json(name = "UNKNOWN")
    UNKNOWN
}
