package ru.mdashlw.hypixel.ranked.util;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mdashlw.hypixel.entities.player.HypixelPlayer;
import ru.mdashlw.hypixel.entities.player.Stats;
import ru.mdashlw.hypixel.entities.player.stats.SkyWars;
import ru.mdashlw.hypixel.ranked.HypixelRankedAPI;
import ru.mdashlw.hypixel.ranked.entities.RankedSeason;

import java.util.HashMap;
import java.util.Map;

public final class HypixelUtil {
    /**
     * Get rating player got in {@code season}.
     * Always 0 for current season.
     *
     * @param skyWars SkyWars stats.
     * @param season  Season.
     * @return Rating in {@code season} or 0.
     */
    public static int getRating(final SkyWars skyWars, final RankedSeason season) {
        return skyWars.get("SkyWars_skywars_rating_" + season.getHypixelDate() + "_rating", 0, JsonNode::asInt);
    }

    /**
     * Get position player got in {@code season}.
     * Always 0 for current season.
     *
     * @param skyWars SkyWars stats.
     * @param season  Season.
     * @return Position in {@code season} or 0.
     */
    public static int getPosition(final SkyWars skyWars, final RankedSeason season) {
        return skyWars.get("SkyWars_skywars_rating_" + season.getHypixelDate() + "_position", 0, JsonNode::asInt);
    }

    /**
     * Get Ranked seasons and rating/position entries.
     *
     * @param api    Hypixel Ranked API instance.
     * @param player Hypixel player.
     * @return Ranked seasons and rating/position entries.
     * @see HypixelRankedAPI#retrieveSeasons()
     */
    public static Map<RankedSeason, RatingPositionEntry> getSeasons(final HypixelRankedAPI api, final HypixelPlayer player) {
        final Map<RankedSeason, RatingPositionEntry> seasons = new HashMap<>();
        final String uuid = player.getUuid();
        final Stats stats = player.getStats();
        final SkyWars skyWars = stats != null ? stats.getSkyWars() : null;

        for (final RankedSeason season : api.getSeasons()) {
            if (season.isHiddenInAPI()) {
                if (season.hasLeaderboard()) {
                    for (final RankedSeason.LeaderboardPlayer leaderboardPlayer : season.getLeaderboard()) {
                        if (leaderboardPlayer.getUuid().equals(uuid)) {
                            final int rating = leaderboardPlayer.getRating();
                            final int position = leaderboardPlayer.getPosition();

                            seasons.put(season, new RatingPositionEntry(rating, position));
                        }
                    }
                }
            } else if (skyWars != null) {
                final int rating = getRating(skyWars, season);
                final int position = getPosition(skyWars, season);

                if (rating != 0 && position != 0) {
                    seasons.put(season, new RatingPositionEntry(rating, position));
                }
            }
        }

        return seasons;
    }
}
