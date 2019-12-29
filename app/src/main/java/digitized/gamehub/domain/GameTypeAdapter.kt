package digitized.gamehub.domain

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter

class GameTypeAdapter : JsonAdapter<GameType>() {
    @FromJson
    override fun fromJson(reader: JsonReader): GameType? {
        return when (reader.nextString()) {
            "VIDEO_GAME" -> GameType.VIDEO_GAME
            "DnD" -> GameType.DnD
            "PARTY_GAME" -> GameType.PARTY_GAME
            "FAMILY_GAME" -> GameType.FAMILY_GAME
            "BOARD_GAME" -> GameType.BOARD_GAME
            "CARD_GAME" -> GameType.CARD_GAME
            else -> null
        }
    }

    override fun toJson(writer: JsonWriter, value: GameType?) {
        writer.value(value.toString())
    }
}
