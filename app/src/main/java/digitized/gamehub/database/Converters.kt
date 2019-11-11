package digitized.gamehub.database

import androidx.room.TypeConverter
import digitized.gamehub.domain.GameType
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun dateFromString(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH).parse(dateString)
    }

    @TypeConverter
    fun dateToString(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH).format(date)
    }

    @TypeConverter
    fun gameTypeToString(type: GameType): String {
        return when (type) {
            GameType.CARD_GAME -> "CARD_GAME"
            GameType.FAMILY_GAME -> "FAMILY_GAME"
            GameType.BOARD_GAME -> "BOARD_GAME"
            GameType.PARTY_GAME -> "PARTY_GAME"
            GameType.DnD -> "DnD"
            GameType.VIDEO_GAME -> "VIDEO_GAME"
            GameType.UNKNOWN -> "UNKOWN"
        }
    }

    @TypeConverter
    fun stringToGameType(gameTypeString: String): GameType {
        return when (gameTypeString) {
            "CARD_GAME" -> GameType.CARD_GAME
            "FAMILY_GAME" -> GameType.FAMILY_GAME
            "BOARD_GAME" -> GameType.BOARD_GAME
            "PARTY_GAME" -> GameType.PARTY_GAME
            "DnD" -> GameType.DnD
            "VIDEO_GAME" -> GameType.VIDEO_GAME
            else -> GameType.UNKNOWN
        }
    }

    @TypeConverter
    fun stringArrayToString(array: Array<String>): String {
        return array.map { a -> "$a," }.toString()
    }

    @TypeConverter
    fun stringToStringArray(string: String): Array<String> {
       return string.split(",", ignoreCase = true).map { a -> a.trim().substring(0, a.length -1) }.toTypedArray()
    }
}
