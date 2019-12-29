package digitized.gamehub.repositories

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Custom Date adapter
 */
class DateAdapter : JsonAdapter<Date>() {
    @FromJson
    override fun fromJson(reader: JsonReader): Date? {
        val value = reader.nextString()
        return SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(value)
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(value!!))
    }
}
