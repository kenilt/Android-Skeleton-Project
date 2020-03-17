package com.kenilt.skeleton.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 3/4/19.
 */
class StringUtilTest {
    @Test
    fun countNumberOfWords_HasNoWords() {
        val res = StringUtil.countNumberOfWords("")
        assertEquals(0, res)
    }

    @Test
    fun countNumberOfWords_Has1WordAtStart() {
        val res = StringUtil.countNumberOfWords("h123 ")
        assertEquals(1, res)
    }

    @Test
    fun countNumberOfWords_Has1WordAtEnd() {
        val res = StringUtil.countNumberOfWords(" 123h")
        assertEquals(1, res)
    }

    @Test
    fun countNumberOfWords_Has1WordAtCenter() {
        val res = StringUtil.countNumberOfWords(" 123h ")
        assertEquals(1, res)
    }

    @Test
    fun countNumberOfWords_HasManySpaceAtStart_Has1Word() {
        val res = StringUtil.countNumberOfWords("       123h ")
        assertEquals(1, res)
    }

    @Test
    fun countNumberOfWords_HasManySpaceAtEnd_Has1Word() {
        val res = StringUtil.countNumberOfWords("       123h     ")
        assertEquals(1, res)
    }

    @Test
    fun countNumberOfWords_Has2Words() {
        val res = StringUtil.countNumberOfWords("123h g987")
        assertEquals(2, res)
    }

    @Test
    fun countNumberOfWords_HasManySpaceInCenter_Has2Words() {
        val res = StringUtil.countNumberOfWords("123h      g987")
        assertEquals(2, res)
    }

    @Test
    fun countNumberOfWords_Has3WordsRandomly() {
        val res = StringUtil.countNumberOfWords("123h 78g  987")
        assertEquals(3, res)
    }

    @Test
    fun trimAll_simpleText() {
        val res = StringUtil.trimAll("text")
        assertEquals("text", res)
    }

    @Test
    fun trimAll_withDoubleBreakLine() {
        val res = StringUtil.trimAll("text\n\rtext")
        assertEquals("text<br>text", res)
    }

    @Test
    fun trimAll_withDoubleBreak() {
        val res = StringUtil.trimAll("text<br><br>text")
        assertEquals("text<br>text", res)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHasNoWords() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("")
        assertEquals("", firstName)
        assertEquals("", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHasASpace() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName(" ")
        assertEquals("", firstName)
        assertEquals("", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHasManySpaces() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName(" ")
        assertEquals("", firstName)
        assertEquals("", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas1Word() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test")
        assertEquals("test", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas1WordAndSpaceAtStart() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName(" test")
        assertEquals("test", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas1WordAndSpaceAtEnd() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test ")
        assertEquals("test", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas1WordAndSpaces() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName(" test ")
        assertEquals("test", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas1WordAndManySpaces() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("    test    ")
        assertEquals("test", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas2Word() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test name")
        assertEquals("name", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas2WordAndManySpacesInCenter() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test     name")
        assertEquals("name", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas2WordAndSpaceAtStart() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName(" test name")
        assertEquals("name", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas2WordAndSpaceAtEnd() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test name ")
        assertEquals("name", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas2WordAndManySpaces() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("  test     name    ")
        assertEquals("name", firstName)
        assertEquals("test", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas3Words() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test 3 words")
        assertEquals("words", firstName)
        assertEquals("test 3", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas3WordsWithManySpacesAtStart() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("  test 3 words")
        assertEquals("words", firstName)
        assertEquals("test 3", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas3WordsWithManySpacesAtEnd() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test 3 words   ")
        assertEquals("words", firstName)
        assertEquals("test 3", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas3WordsWithManySpacesAtCenter() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test   3    words")
        assertEquals("words", firstName)
        assertEquals("test 3", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHas3WordsWithManySpaces() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("  test   3    words  ")
        assertEquals("words", firstName)
        assertEquals("test 3", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHasSpecialWords() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("test special word %@#$%@#")
        assertEquals("%@#\$%@#", firstName)
        assertEquals("test special word", lastName)
    }

    @Test
    fun getFirstLastNameFromFullName_whenFullNameHasSpecialWordsWithSpace() {
        val (firstName, lastName) = StringUtil.getFirstLastNameFromFullName("  test   special word   %@#$%@# ")
        assertEquals("%@#\$%@#", firstName)
        assertEquals("test special word", lastName)
    }
}
