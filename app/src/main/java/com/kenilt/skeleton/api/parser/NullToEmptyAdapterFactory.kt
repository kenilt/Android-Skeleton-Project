package com.kenilt.skeleton.api.parser

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken


/**
 * Created by thangnguyen on 4/5/18.
 */
@Suppress("UNCHECKED_CAST")
internal class NullToEmptyAdapterFactory : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {

        val delegate = gson.getDelegateAdapter(this, type)
        val rawType = type.rawType as? Class<T>
        return when (rawType) {
            String::class.java -> {
                StringTypeAdapter() as? TypeAdapter<T>
            }
            List::class.java -> {
                val mutableListDelegate = delegate as? TypeAdapter<MutableList<Any>>
                MutableListTypeAdapter(mutableListDelegate) as? TypeAdapter<T>
            }
            else -> {
                null
            }
        }
    }
}
