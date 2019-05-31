package ru.mdashlw.hypixel.api.ranked.entities

class Leaderboard : ArrayList<Leaderboard.Player>() {
    data class Player(
        val name: String,
        val rating: Int,
        val position: Int,
        val wins: Int,
        val kills: Int
    )
}
