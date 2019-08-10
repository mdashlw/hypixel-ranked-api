package ru.mdashlw.hypixel.ranked.util;

import com.fasterxml.jackson.databind.JsonNode;
import ru.mdashlw.hypixel.entities.player.HypixelPlayer;
import ru.mdashlw.hypixel.entities.player.Stats;
import ru.mdashlw.hypixel.entities.player.stats.SkyWars;
import ru.mdashlw.hypixel.ranked.HypixelRankedAPI;
import ru.mdashlw.hypixel.ranked.entities.RankedSeason;

import java.util.HashMap;
import java.util.Map;

public class HypixelUtil {
    /**
     * Get rating player got in {@code season}.
     * Always 0 for current season.
     *
     * @param skyWars SkyWars stats.
     * @param season  Season.
     * @return Rating in {@code season} or 0.
     */
    public static int getRating(SkyWars skyWars, RankedSeason season) {
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
    public static int getPosition(SkyWars skyWars, RankedSeason season) {
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
    public static Map<RankedSeason, RatingPositionEntry> getSeasons(HypixelRankedAPI api, HypixelPlayer player) {
        Map<RankedSeason, RatingPositionEntry> seasons = new HashMap<>();
        String uuid = player.getUuid();
        Stats stats = player.getStats();
        SkyWars skyWars = stats == null ? null : stats.getSkyWars();

        for (RankedSeason season : api.getSeasons()) {
            if (season.isHiddenInAPI()) {
                if (!season.hasLeaderboard()) {
                    continue;
                }

                for (RankedSeason.LeaderboardPlayer leaderboardPlayer : season.getLeaderboard()) {
                    if (!leaderboardPlayer.getUuid().equals(uuid)) {
                        continue;
                    }

                    int rating = leaderboardPlayer.getRating();
                    int position = leaderboardPlayer.getPosition();

                    seasons.put(season, new RatingPositionEntry(rating, position));
                }
            } else if (skyWars != null) {
                int rating = getRating(skyWars, season);
                int position = getPosition(skyWars, season);

                if (rating == 0 || position == 0) {
                    continue;
                }

                seasons.put(season, new RatingPositionEntry(rating, position));
            }
        }

        return seasons;
    }
}
