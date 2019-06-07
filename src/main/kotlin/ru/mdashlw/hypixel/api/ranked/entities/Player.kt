package ru.mdashlw.hypixel.api.ranked.entities

data class Player(
    val uuid: String,
    val name: String,
    val rank: String?,
    val guild: String?,
    val rating: Int,
    val position: Int
)
