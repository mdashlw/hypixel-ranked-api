package ru.mdashlw.hypixel.api.ranked.reply.impl

import ru.mdashlw.hypixel.api.ranked.elements.Leaderboard
import ru.mdashlw.hypixel.api.ranked.reply.Reply

class LeaderboardReply(
    success: Boolean,
    cause: String?,
    leaderboard: Leaderboard?
) : Reply<Leaderboard>(success, cause, leaderboard)
