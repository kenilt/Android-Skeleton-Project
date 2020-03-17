package com.kenilt.skeleton.extension

import java.io.*

/**
 * Created by thangnguyen on 2019-08-16.
 */
fun <T : Serializable> T.deepCopy(): T {
    val baos = ByteArrayOutputStream()
    val oos  = ObjectOutputStream(baos)
    oos.writeObject(this)
    oos.close()
    val bais = ByteArrayInputStream(baos.toByteArray())
    val ois  = ObjectInputStream(bais)
    @Suppress("unchecked_cast")
    return ois.readObject() as T
}
