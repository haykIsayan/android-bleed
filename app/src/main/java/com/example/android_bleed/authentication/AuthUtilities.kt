package com.example.android_bleed.authentication

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.android_bleed.data.models.User
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object AuthUtilities {
    var sCurrentUser: User? = null

    private const val SHA_256 = "SHA-256"
    private const val AES = "AES"

    private const val PASSWORD = "ANDROID_LEGENDS_TEST_PASSWORD"


    fun isPasswordValid(password: String, passwordCode: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            password == decryptPassword(passwordCode)
        } else {
            false
        }


    @RequiresApi(Build.VERSION_CODES.O)
    fun encryptPassword(password: String): String {
        val secretKeySpec = generateKey()
        val cipher = Cipher.getInstance(AES)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val encVal = cipher.doFinal(password.toByteArray())
        return Base64.getEncoder().encodeToString(encVal)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun decryptPassword(password: String): String {
        val secretKeySpec = generateKey()

        val cipher = Cipher.getInstance(AES)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec)

        val decodedValue = Base64.getDecoder().decode(password)
        val decValue = cipher.doFinal(decodedValue)

        return String(decValue)
    }

    private fun generateKey(): SecretKeySpec {
        val digest = MessageDigest.getInstance(SHA_256)
        val bytes = PASSWORD.toByteArray()
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, AES)
    }

}