package ru.mdashlw.hypixel.api.ranked.reply.impl

import ru.mdashlw.hypixel.api.ranked.entities.Season
import ru.mdashlw.hypixel.api.ranked.reply.Reply

class SeasonsReply(
    success: Boolean,
    cause: String?,
    seasons: List<Season>?
) : Reply<List<Season>>(success, cause, seasons)
