package com.kenilt.skeleton.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class VNCharacterUtilsTest {

    @Test
    fun removeAccent_OneCharacterInVNWillPassed() {
        val res = VNCharacterUtils.removeAccent("ấ")
        assertEquals("a", res)
    }

    @Test
    fun removeAccent_OneCharacterInVNWillFailed() {
        val res = VNCharacterUtils.removeAccent("ấ")
        assertNotEquals("ấ", res)
    }

    @Test
    fun removeAccent_OneCharacterInENWillPassed() {
        val res = VNCharacterUtils.removeAccent("a")
        assertEquals("a", res)
    }

    @Test
    fun removeAccent_OneCharacterInENWillFailed() {
        val res = VNCharacterUtils.removeAccent("a")
        assertNotEquals("ấ", res)
    }

    @Test
    fun removeAccent_OneWordInVNWillPassed() {
        val res = VNCharacterUtils.removeAccent("cất")
        assertEquals("cat", res)
    }

    @Test
    fun removeAccent_OneWordInVNWillFailed() {
        val res = VNCharacterUtils.removeAccent("cất")
        assertNotEquals("cất", res)
    }

    @Test
    fun removeAccent_OneWordInENWillPassed() {
        val res = VNCharacterUtils.removeAccent("cat")
        assertEquals("cat", res)
    }

    @Test
    fun removeAccent_OneWordInENWillFailed() {
        val res = VNCharacterUtils.removeAccent("cat")
        assertNotEquals("cất", res)
    }

    @Test
    fun removeAccent_OneWordsInVNWillPassed() {
        val res = VNCharacterUtils.removeAccent("cất vô, điên quá")
        assertEquals("cat vo, dien qua", res)
    }

    @Test
    fun removeAccent_OneWordsInVNWillFailed() {
        val res = VNCharacterUtils.removeAccent("cất vô, điên quá")
        assertNotEquals("cất vô, điên quá", res)
    }

    @Test
    fun removeAccent_OneWordsInENWillPassed() {
        val res = VNCharacterUtils.removeAccent("cat dog change")
        assertEquals("cat dog change", res)
    }

    @Test
    fun removeAccent_OneWordsInENWillFailed() {
        val res = VNCharacterUtils.removeAccent("cat vo di")
        assertNotEquals("cất vô đi", res)
    }

}
