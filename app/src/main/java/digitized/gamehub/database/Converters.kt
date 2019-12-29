package digitized.gamehub.database

import androidx.room.TypeConverter
import digitized.gamehub.domain.GameType
import digitized.gamehub.domain.Location
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    /**
     * Get date from string
     */
    @TypeConverter
    fun dateFromString(dateString: String): Date? {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateString)
    }

    /**
     * Turn date into string
     */
    @TypeConverter
    fun dateToString(date: Date): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(date)
    }

    /**
     * Turn gametype into string
     */
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

    /**
     * Get gametype from string
     */
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

    /**
     * Turn array into string
     */
    @TypeConverter
    fun stringArrayToString(array: Array<String>): String {
        return array.joinToString(",")
    }

    /**
     * Turn string into array
     */
    @TypeConverter
    fun stringToStringArray(string: String): Array<String> {
        return string.split(",", ignoreCase = true).map { a -> a.trim() }.toTypedArray()
    }

    /**
     * Only save the coordinates in the database
     */
    @TypeConverter
    fun locationToString(location: Location): String {
        val coords = "${location.coordinates[0]},${location.coordinates[1]},"
        return coords
    }

    /**
     * Get the coordinates to a location from the database
     */
    @TypeConverter
    fun stringToLocation(string: String): Location {
        val coords = string.split(",").filter { !it.isEmpty() }.map { it.toDouble() }.toDoubleArray()
        return Location("Point", coords)
    }
}
