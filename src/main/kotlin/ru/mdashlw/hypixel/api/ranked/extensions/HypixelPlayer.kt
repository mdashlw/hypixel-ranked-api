package ru.mdashlw.hypixel.api.ranked.extensions

import ru.mdashlw.hypixel.api.entities.player.HypixelPlayer
import ru.mdashlw.hypixel.api.ranked.HypixelRankedApi
import ru.mdashlw.hypixel.api.ranked.entities.Season

val HypixelPlayer.rankedSeasons: Map<Season, Pair<Int, Int>>
    get() {
        val seasons = HypixelRankedApi.seasons.asSequence()

        return seasons
            .filter(Season::isHiddenInAPI)
            .map {
                val player = it.leaderboard?.findByUuid(uuid)
                    ?: return@map null

                it to (player.rating to player.position)
            }
            .plus(
                seasons
                    .filterNot(Season::isHiddenInAPI)
                    .map { season ->
                        val game = stats?.SkyWars ?: return@map null

                        val rating = game.getRating(season)
                        val position = game.getPosition(season)

                        if (rating == 0 && position == 0) {
                            return@map null
                        }

                        season to (rating to position + 1)
                    }
            )
            .filterNotNull()
            .toMap()
    }
