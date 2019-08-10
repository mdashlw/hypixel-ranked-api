package ru.mdashlw.hypixel.ranked;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import ru.mdashlw.hypixel.ranked.entities.Game;
import ru.mdashlw.hypixel.ranked.entities.LeaderboardPlayer;
import ru.mdashlw.hypixel.ranked.entities.RankedPlayer;
import ru.mdashlw.hypixel.ranked.entities.RankedSeason;
import ru.mdashlw.hypixel.ranked.exceptions.HypixelRankedApiException;
import ru.mdashlw.hypixel.ranked.reply.Reply;
import ru.mdashlw.hypixel.ranked.reply.impl.GameReply;
import ru.mdashlw.hypixel.ranked.reply.impl.LeaderboardReply;
import ru.mdashlw.hypixel.ranked.reply.impl.PlayerReply;
import ru.mdashlw.hypixel.ranked.reply.impl.SeasonsReply;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Hypixel Ranked API.
 */
public class HypixelRankedAPI {
    /**
     * Base URL for the Hypixel Ranked API.
     */
    public static final String BASE_URL = "https://hypixel-ranked-api.herokuapp.com/";
    /**
     * Date of the first Ranked season.
     */
    public static final LocalDate FIRST_SEASON_DATE = LocalDate.of(2016, Month.APRIL, 1);
    /**
     * Date formatter used in the Hypixel API.
     */
    public static final DateTimeFormatter HYPIXEL_DATE_FORMATTER = DateTimeFormatter.ofPattern("M_yy");

    private OkHttpClient okHttpClient;
    private ObjectMapper objectMapper;

    private List<RankedSeason> seasons = Collections.emptyList();

    public HypixelRankedAPI() {
        this(new OkHttpClient.Builder()
                .connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .build());
    }

    /**
     * @param okHttpClient OkHttpClient instance.
     */
    public HypixelRankedAPI(OkHttpClient okHttpClient) {
        this(okHttpClient, new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }

    /**
     * @param okHttpClient OkHttpClient instance.
     * @param objectMapper ObjectMapper instance.
     */
    public HypixelRankedAPI(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve Ranked player.
     *
     * @param name Name or UUID.
     * @return Completable future of RankedPlayer.
     */
    public CompletableFuture<RankedPlayer> retrievePlayer(String name) {
        return retrieve(PlayerReply.class, "player/" + name);
    }

    /**
     * Retrieve Ranked leaderboard.
     *
     * @return Completable future of List of LeaderboardPlayer.
     */
    public CompletableFuture<List<LeaderboardPlayer>> retrieveLeaderboard() {
        return retrieve(LeaderboardReply.class, "leaderboard");
    }

    /**
     * Retrieve Ranked seasons.
     * Also updates variable {@code seasons}.
     *
     * @return Completable future of List of RankedSeason.
     */
    public CompletableFuture<List<RankedSeason>> retrieveSeasons() {
        return retrieve(SeasonsReply.class, "seasons")
                .thenApply(seasons -> {
                    this.seasons = seasons;
                    return seasons;
                });
    }

    /**
     * Retrieve game stats by game id.
     *
     * @param id Game id.
     * @return Completable future of Game.
     */
    public CompletableFuture<Game> retrieveGame(String id) {
        return retrieve(GameReply.class, "game/" + id);
    }

    private <T> CompletableFuture<T> retrieve(Class<? extends Reply<T>> clazz, String endpoint) {
        String url = BASE_URL + endpoint;
        CompletableFuture<T> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody body = response.body();

                // Body is never null in Callback#onResponse.
                if (body == null) {
                    future.completeExceptionally(new IllegalStateException("Response body is null"));
                    return;
                }

                // Don't continue if we got a bad response,
                // usually it means the body is an html page
                // and we will get a json parse exception.
                if (!response.isSuccessful()) {
                    body.close();
                    future.completeExceptionally(new IllegalStateException("Response was not successful"));
                    return;
                }

                Reply<T> reply;

                try {
                    reply = objectMapper.readValue(body.string(), clazz);
                } catch (IOException exception) {
                    future.completeExceptionally(exception);
                    return;
                }

                if (reply.isSuccess()) {
                    future.complete(reply.getValue());
                } else {
                    future.completeExceptionally(new HypixelRankedApiException(reply.getCause()));
                }
            }
        });

        return future;
    }

    /**
     * Get season with specific date.
     * Only year and month matter.
     *
     * @param date Date.
     * @return Season with {@code date}.
     */
    public RankedSeason getSeasonByDate(LocalDate date) {
        for (RankedSeason season : seasons) {
            LocalDate seasonDate = season.getDate();

            if (seasonDate.getYear() == date.getYear() && seasonDate.getMonthValue() == date.getMonthValue()) {
                return season;
            }
        }

        return null;
    }

    /**
     * Current Ranked season.
     *
     * @return Current Ranked season.
     */
    public RankedSeason getCurrentSeason() {
        RankedSeason season = getSeasonByDate(LocalDate.now());

        if (season == null) {
            throw new IllegalStateException("Couldn't find the current season");
        }

        return season;
    }

    /**
     * Stored Ranked seasons.
     *
     * @return Stored Ranked seasons.
     * @see HypixelRankedAPI#retrieveSeasons()
     */
    public List<RankedSeason> getSeasons() {
        return seasons;
    }
}
