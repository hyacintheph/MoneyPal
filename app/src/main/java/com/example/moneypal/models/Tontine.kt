package com.example.moneypal.models

data class Tontine(
    var name: String = "",
    var description: String = "",
    var totalAmount: Int = 0,
    var notifyAllMembers: Boolean = false,
    var users: List<UserData> = listOf(),
    var sessions: List<Session> = listOf(),
    var ownerUser: UserData = UserData(),

    )

