package com.example.groceryshopapp.utils

import android.util.Patterns

object TextUtils {

    fun isValidEmail(target: String): Boolean {
        return if (target.isEmpty()) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }


}