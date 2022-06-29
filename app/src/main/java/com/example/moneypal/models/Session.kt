package com.example.moneypal.models

import com.google.type.DateTime

data class Session(
    val session_id: Int = 0,
    var contributed: List<User> = listOf(),
    var deadline: DateTime? = null,
    var beneficiary: User? = null,
    var amountToSend: Int = 0,
    var recipient: User? = null,
)
