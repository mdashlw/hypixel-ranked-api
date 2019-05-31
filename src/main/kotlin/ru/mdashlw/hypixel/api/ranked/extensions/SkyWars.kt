package ru.mdashlw.hypixel.api.ranked.extensions

import com.fasterxml.jackson.databind.JsonNode
import ru.mdashlw.hypixel.api.entities.player.stats.games.SkyWars
import ru.mdashlw.hypixel.api.ranked.entities.Season
import ru.mdashlw.hypixel.api.util.get
import ru.mdashlw.hypixel.api.util.int

fun SkyWars.getRating(season: Season): Int =
    get("SkyWars_skywars_rating_${season.hypixelDate}_rating", 0, JsonNode::int)

fun SkyWars.getPosition(season: Season): Int =
    get("SkyWars_skywars_rating_${season.hypixelDate}_position", 0, JsonNode::int)
