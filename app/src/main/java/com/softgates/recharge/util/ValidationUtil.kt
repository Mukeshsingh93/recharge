package com.softgates.recharge.util

import androidx.core.util.PatternsCompat
import java.util.regex.Pattern

object ValidationUtil {

    private val EmailValidation by lazy { Pattern.compile("[a-zA-Z0-9_\\-\\&\\*\\+='/\\{\\}~][a-zA-Z0-9_\\-\\.&\\*\\+='/\\{\\}~]* ") }

    fun isEmailValid(email: String) = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()


}