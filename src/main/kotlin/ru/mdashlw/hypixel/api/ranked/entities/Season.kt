package ru.mdashlw.hypixel.api.ranked.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import ru.mdashlw.hypixel.api.enums.RankedDivision
import ru.mdashlw.hypixel.api.enums.RankedReward
import ru.mdashlw.hypixel.api.ranked.HypixelRankedApi
import java.time.LocalDate

data class Season(
    val number: Int,
    val leaderboard: Leaderboard? = null,
    @JsonProperty("hiddenInAPI") val isHiddenInAPI: Boolean = false
) {
    @JsonIgnore
    val date: LocalDate = HypixelRankedApi.firstSeasonDate.plusMonths(number - 1L)
    @JsonIgnore
    val hypixelDate: String = date.format(HypixelRankedApi.hypixelDateFormatter)

    val hasLeaderboard: Boolean
        @JsonIgnore
        get() = leaderboard != null

    @JsonIgnore
    val rewards: Map<RankedDivision, List<RankedReward>> = mapOf(
        RankedDivision.MASTERS to listOf(
            RankedReward.DRAGON_RIDER,
            RankedDivision.MASTERS.rewards[number % 2]
        ),
        RankedDivision.DIAMOND to listOf(
            RankedReward.MAGIC_BOX,
            RankedDivision.DIAMOND.rewards[number % 3],
            RankedDivision.DIAMOND.rewards[number % 4 + 3]
        ),
        RankedDivision.GOLD to listOf(
            RankedDivision.GOLD.rewards[number % 3]
        )
    )

    class Leaderboard : ArrayList<Leaderboard.Player>() {
        fun findByName(name: String): Player? =
            find { it.name == name }

        fun findByUuid(uuid: String): Player? =
            find { it.uuid == uuid }

        data class Player(
            val name: String,
            val uuid: String,
            val rating: Int,
            val position: Int
        )
    }
}
