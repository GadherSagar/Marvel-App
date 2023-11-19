package com.example.marvelapp.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String?.isValidString(): Boolean {
    return this != null && !this.equals("", ignoreCase = true) && !this.equals(
        "null",
        ignoreCase = true
    )
}

@Suppress("MagicNumber")
fun String.md5(): String {
    val md5 = "MD5"
    try {
        // Create MD5 Hash
        val digest = MessageDigest
            .getInstance(md5)
        digest.update(this.toByteArray())
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            val h = StringBuilder(Integer.toHexString(0xFF and aMessageDigest.toInt()))
            while (h.length < 2) h.insert(0, "0")
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        println(e.message)
    }
    return ""
}
