package digitized.gamehub.repositories

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter: JsonAdapter<Date>() {
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