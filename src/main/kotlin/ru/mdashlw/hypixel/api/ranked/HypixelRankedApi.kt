package ru.mdashlw.hypixel.api.ranked

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import ru.mdashlw.hypixel.api.ranked.entities.Leaderboard
import ru.mdashlw.hypixel.api.ranked.entities.Player
import ru.mdashlw.hypixel.api.ranked.entities.Season
import ru.mdashlw.hypixel.api.ranked.exceptions.HypixelRankedApiException
import ru.mdashlw.hypixel.api.ranked.reply.Reply
import ru.mdashlw.hypixel.api.ranked.reply.impl.LeaderboardReply
import ru.mdashlw.hypixel.api.ranked.reply.impl.PlayerReply
import ru.mdashlw.hypixel.api.ranked.reply.impl.SeasonsReply
import ru.mdashlw.hypixel.api.ranked.util.newCall
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object HypixelRankedApi {
    const val BASE_URL = "https://hypixel-ranked-api.herokuapp.com/"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    private val jackson: ObjectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val firstSeasonDate: LocalDate = LocalDate.of(2016, Month.APRIL, 1)
    val hypixelDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M_yy")

    lateinit var seasons: List<Season>

    val currentSeason: Season
        get() = findSeasonByDate(LocalDate.now())
            ?: error("Cannot find the current season")

    fun retrievePlayer(name: String) = get<PlayerReply, Player>("player/$name")

    fun retrieveLeaderboard() = get<LeaderboardReply, Leaderboard>("leaderboard")

    fun retrieveSeasons() = get<SeasonsReply, List<Season>>("seasons")?.also { seasons = it }

    fun findSeasonByDate(date: LocalDate): Season? =
        seasons.find { it.date.year == date.year && it.date.monthValue == date.monthValue }

    inline fun <reified R : Reply<T>, T> get(endpoint: String) = get(R::class, endpoint)

    fun <R : Reply<T>, T> get(replyClass: KClass<R>, endpoint: String): T? {
        val url = "$BASE_URL$endpoint"

        val response = okHttpClient.newCall(url)
        val reply = jackson.readValue(response, replyClass.java)

        reply.run {
            if (!success) {
                throw HypixelRankedApiException(cause!!)
            }

            return element
        }
    }
}
