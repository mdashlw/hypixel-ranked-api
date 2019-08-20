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
public final class HypixelRankedAPI {
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

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

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
    public HypixelRankedAPI(final OkHttpClient okHttpClient) {
        this(okHttpClient, new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }

    /**
     * @param okHttpClient OkHttpClient instance.
     * @param objectMapper ObjectMapper instance.
     */
    public HypixelRankedAPI(final OkHttpClient okHttpClient, final ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieve Ranked player.
     *
     * @param name Name or UUID.
     * @return Completable future of RankedPlayer.
     */
    public CompletableFuture<RankedPlayer> retrievePlayer(final String name) {
        return this.retrieve(PlayerReply.class, "player/" + name);
    }

    /**
     * Retrieve Ranked leaderboard.
     *
     * @return Completable future of List of LeaderboardPlayer.
     */
    public CompletableFuture<List<LeaderboardPlayer>> retrieveLeaderboard() {
        return this.retrieve(LeaderboardReply.class, "leaderboard");
    }

    /**
     * Retrieve Ranked seasons.
     * Also updates variable {@code seasons}.
     *
     * @return Completable future of List of RankedSeason.
     */
    public CompletableFuture<List<RankedSeason>> retrieveSeasons() {
        return this.retrieve(SeasonsReply.class, "seasons")
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
    public CompletableFuture<Game> retrieveGame(final String id) {
        return this.retrieve(GameReply.class, "game/" + id);
    }

    private <T> CompletableFuture<T> retrieve(final Class<? extends Reply<T>> clazz, final String endpoint) {
        final String url = BASE_URL + endpoint;
        final CompletableFuture<T> future = new CompletableFuture<>();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                final ResponseBody body = response.body();

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
                    future.completeExceptionally(new IllegalStateException("Response was not successful, status code: " + response.code()));
                    return;
                }

                final Reply<T> reply;

                try {
                    reply = HypixelRankedAPI.this.objectMapper.readValue(body.string(), clazz);
                } catch (final IOException exception) {
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
    public RankedSeason getSeasonByDate(final LocalDate date) {
        for (final RankedSeason season : this.seasons) {
            final LocalDate seasonDate = season.getDate();

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
        final RankedSeason season = this.getSeasonByDate(LocalDate.now());

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
        return this.seasons;
    }
}
