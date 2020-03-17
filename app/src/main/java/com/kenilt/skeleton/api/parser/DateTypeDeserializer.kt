package com.kenilt.skeleton.api.parser

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author neal
 * @since 7/21/16
 */
class DateTypeDeserializer : JsonDeserializer<Date> {
    companion object {
        private val TAG = DateTypeDeserializer::class.java.name
        private val DATE_FORMATS = arrayOf("yyyy-MM-dd HH:mm:ss")
    }

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, typeOF: Type, context: JsonDeserializationContext): Date {
        for (format in DATE_FORMATS) {
            try {
                return SimpleDateFormat(format, Locale.getDefault()).parse(jsonElement.asString)
            } catch (e: ParseException) {
                Log.v(TAG, "Error when deserialize date")
            }
        }
        throw JsonParseException("Cant parse date: \"" + jsonElement.asString
                + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS))
    }
}
