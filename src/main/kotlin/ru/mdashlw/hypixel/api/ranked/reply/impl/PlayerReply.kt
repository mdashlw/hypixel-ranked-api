package ru.mdashlw.hypixel.api.ranked.reply.impl

import ru.mdashlw.hypixel.api.ranked.entities.Player
import ru.mdashlw.hypixel.api.ranked.reply.Reply

class PlayerReply(
    success: Boolean,
    cause: String?,
    player: Player?
) : Reply<Player>(success, cause, player)
