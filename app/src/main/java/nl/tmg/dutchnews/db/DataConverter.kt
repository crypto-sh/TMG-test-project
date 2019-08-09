package nl.tmg.dutchnews.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import nl.tmg.dutchnews.dto.Source
import java.util.*


@TypeConverters
class DataConverter {

    @TypeConverter
    fun convertSource(source: Source) : String {
        return Gson().toJson(source)!!
    }

    @TypeConverter
    fun convertToSource(source : String) : Source {
        return Gson().fromJson(source,Source::class.java)
    }

    @TypeConverter
    fun convertDateToString(date : Date) : Long {
        return date.time
    }

    @TypeConverter
    fun convertStringToDate(date : Long) : Date {
        return Date(date)
    }
}