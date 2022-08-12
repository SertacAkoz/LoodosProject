package com.sertac.loodosproject.util

import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


object Utils {
    fun emailValidation(email:String): Boolean {
        if (email.isEmpty()){
            return false
        }else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(date:String): String {
        var firstApiFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
        firstApiFormat = firstApiFormat.withLocale( Locale.ENGLISH )
        val formattedDate = LocalDate.parse(date, firstApiFormat)

        return formattedDate.toString()
    }

    fun hashInput(data:String): String {
        val bytes = data.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun removeInvalidCharacters(string:String): String {
        val re = Regex("[^A-Za-z0-9 ]")

        val returnString = re.replace(string,"")

        return returnString
    }
}