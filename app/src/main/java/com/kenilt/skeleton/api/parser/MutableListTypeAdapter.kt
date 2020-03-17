package com.kenilt.skeleton.api.parser

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * Created by Kenilt Nguyen on 8/1/18.
 */
class MutableListTypeAdapter<T>(private val delegate: TypeAdapter<MutableList<T>>?) : TypeAdapter<MutableList<T>>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: MutableList<T>?) {
        delegate?.write(out, value)
    }

    @Throws(IOException::class)
    override fun read(reader: JsonReader?): MutableList<T> {
        if (reader?.peek() === JsonToken.NULL) {
            reader.nextNull()
            return ArrayList()
        }
        return delegate?.read(reader) ?: ArrayList()
    }
}
