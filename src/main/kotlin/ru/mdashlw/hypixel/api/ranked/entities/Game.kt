package ru.mdashlw.hypixel.api.ranked.entities

data class Game(
    val type: String,
    val mode: String,
    val map: String,
    val winner: String
)
