package com.example.moneypal.models

import android.net.Uri

data class User(
    var name: String? = null,
    var avatar: Uri? = null,
    var phoneNumber: String? = null,
    var notification: Boolean = false,
    var darkMode: Boolean = false
)

data class UserData(
    var name: String? = null,
    var avatar: String = "",
    var phoneNumber: String? = null,
    var notification: Boolean = false,
    var darkMode: Boolean = false,
    var clicked: Boolean = false,
)