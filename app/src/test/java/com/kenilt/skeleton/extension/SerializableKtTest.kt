package com.kenilt.skeleton.extension

import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.Serializable

/**
 * Created by thangnguyen on 2019-09-24.
 */
class SerializableKtTest {

    @Test
    fun deepCopy() {
        val example = ExampleSerializable(1, "test")
        val clone = example.deepCopy()
        assertEquals(1, clone.id)
        assertEquals("test", clone.content)
    }

    data class ExampleSerializable (
        var id: Int = 0,
        var content: String? = null
    ) : Serializable
}
