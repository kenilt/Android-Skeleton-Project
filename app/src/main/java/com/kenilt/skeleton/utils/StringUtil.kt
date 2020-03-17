package com.kenilt.skeleton.utils

/**
 * Created by neal on 2/10/17.
 */

object StringUtil {
    fun countNumberOfWords(text: String): Int {
        var wordCount = 0

        var word = false
        val endOfLine = text.length - 1

        for (i in 0 until text.length) {
            // if the char is a letter, word = true.
            if (Character.isLetterOrDigit(text[i]) && i != endOfLine) {
                word = true
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetterOrDigit(text[i]) && word) {
                wordCount++
                word = false
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetterOrDigit(text[i]) && i == endOfLine) {
                wordCount++
            }
        }

        return wordCount
    }

    fun trimAll(s: String): String {
        return s.trim().replace("[\n\r]".toRegex(), "<br>").replace("<br><br>", "<br>")
    }

    fun getFirstLastNameFromFullName(fullName: String?): Pair<String, String> {
        var firstName = ""
        var lastName = ""
        val splits = fullName?.trim()?.split("\\s+".toRegex())
        if (splits != null && splits.isNotEmpty()) {
            firstName = splits.last()
            lastName = if (splits.size == 1) {
                firstName
            } else {
                splits.filterIndexed { index, _ -> index != splits.size - 1 }.joinToString(" ")
            }
        }
        return Pair(firstName, lastName)
    }
}
