package ru.mdashlw.hypixel.api.ranked.reply.impl

import ru.mdashlw.hypixel.api.ranked.entities.Game
import ru.mdashlw.hypixel.api.ranked.reply.Reply

class GameReply(
    success: Boolean,
    cause: String?,
    game: Game?
) : Reply<Game>(success, cause, game)
