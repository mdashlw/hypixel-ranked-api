package ru.mdashlw.hypixel.api.ranked

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import ru.mdashlw.hypixel.api.ranked.elements.Leaderboard
import ru.mdashlw.hypixel.api.ranked.elements.Player
import ru.mdashlw.hypixel.api.ranked.exceptions.HypixelRankedApiException
import ru.mdashlw.hypixel.api.ranked.reply.Reply
import ru.mdashlw.hypixel.api.ranked.util.newCall
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object HypixelRankedAPI {
    private const val BASE_URL = "https://hypixel-ranked-api.herokuapp.com/"

    private val jackson: ObjectMapper = jacksonObjectMapper()
    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

    fun getPlayerByName(name: String): Player? = get("player/$name")

    fun getLeaderboard(): Leaderboard? = get("leaderboard")

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

    inline fun <reified R : Reply<T>, T> get(endpoint: String): T? = get(R::class, endpoint)
}
