package edu.uvg.uvgchatejemplo.data

// CaesarCipherUtil.kt


object CaesarCipherUtil {
    fun encrypt(text: String, shift: Int): String {
        return text.map { char ->
            when {
                char.isUpperCase() -> {
                    val shifted = ((char.code - 'A'.code + shift) % 26 + 'A'.code)
                    shifted.toChar()
                }
                char.isLowerCase() -> {
                    val shifted = ((char.code - 'a'.code + shift) % 26 + 'a'.code)
                    shifted.toChar()
                }
                else -> char
            }
        }.joinToString("")
    }

    fun decrypt(text: String, shift: Int): String {
        return encrypt(text, 26 - shift)
    }
}
