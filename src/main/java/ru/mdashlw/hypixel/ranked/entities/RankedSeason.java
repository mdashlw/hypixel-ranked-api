package ru.mdashlw.hypixel.ranked.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mdashlw.hypixel.entities.player.stats.SkyWars;
import ru.mdashlw.hypixel.ranked.HypixelRankedAPI;

import java.time.LocalDate;
import java.util.*;

/**
 * Ranked season.
 */
public final class RankedSeason {
    private final int number;
    private List<LeaderboardPlayer> leaderboard;
    private boolean hiddenInAPI;

    public RankedSeason(final int number) {
        this.number = number;
    }

    public RankedSeason(@JsonProperty("number") final int number, @JsonProperty("leaderboard") final List<LeaderboardPlayer> leaderboard, @JsonProperty("hiddenInAPI") final boolean hiddenInAPI) {
        this.number = number;
        this.leaderboard = leaderboard;
        this.hiddenInAPI = hiddenInAPI;
    }

    /**
     * Returns whether this season has leaderboard.
     *
     * @return Does this season have leaderboard.
     */
    public boolean hasLeaderboard() {
        return this.leaderboard != null;
    }

    /**
     * Date of this season.
     *
     * @return Date of this season.
     */
    public LocalDate getDate() {
        return HypixelRankedAPI.FIRST_SEASON_DATE.plusMonths(this.number - 1);
    }

    /**
     * Hypixel-style date of this season.
     *
     * @return Hypixel-style date of this season.
     */
    public String getHypixelDate() {
        return HypixelRankedAPI.HYPIXEL_DATE_FORMATTER.format(this.getDate());
    }

    /**
     * Ranked rewards for this season.
     *
     * @return Ranked rewards for this season.
     */
    public Map<SkyWars.RankedDivision, List<SkyWars.RankedReward>> getRewards() {
        final Map<SkyWars.RankedDivision, List<SkyWars.RankedReward>> rewards = new EnumMap<>(SkyWars.RankedDivision.class);
        final List<SkyWars.RankedReward> mastersRewards = SkyWars.RankedDivision.MASTERS.getRewards();
        final List<SkyWars.RankedReward> diamondRewards = SkyWars.RankedDivision.DIAMOND.getRewards();
        final List<SkyWars.RankedReward> goldRewards = SkyWars.RankedDivision.GOLD.getRewards();

        {
            final List<SkyWars.RankedReward> rewardList = new ArrayList<>();

            rewardList.add(SkyWars.RankedReward.DRAGON_RIDER);
            rewardList.add(mastersRewards.get(this.number % 2));

            rewards.put(SkyWars.RankedDivision.MASTERS, rewardList);
        }

        {
            final List<SkyWars.RankedReward> rewardList = new ArrayList<>();

            rewardList.add(SkyWars.RankedReward.MAGIC_BOX);
            rewardList.add(diamondRewards.get(this.number % 3));
            rewardList.add(diamondRewards.get(this.number % 4 + 3));

            rewards.put(SkyWars.RankedDivision.DIAMOND, rewardList);
        }

        rewards.put(SkyWars.RankedDivision.GOLD, Collections.singletonList(goldRewards.get(this.number % 3)));

        return rewards;
    }

    /**
     * Season number, e.g. 45.
     *
     * @return Season number.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Stored season leaderboard.
     *
     * @return Stored season leaderboard.
     */
    public List<LeaderboardPlayer> getLeaderboard() {
        return this.leaderboard;
    }

    /**
     * Returns whether rating/position for this season
     * are hidden in the Hypixel API.
     *
     * @return Are rating/position hidden for this season.
     */
    public boolean isHiddenInAPI() {
        return this.hiddenInAPI;
    }

    /**
     * Leaderboard player.
     */
    public static final class LeaderboardPlayer {
        private String uuid;
        private String name;
        private int rating;
        private int position;

        /**
         * Undashed UUID.
         *
         * @return Undashed UUID.
         */
        public String getUuid() {
            return this.uuid;
        }

        /**
         * Player name.
         *
         * @return Player name.
         */
        public String getName() {
            return this.name;
        }

        /**
         * Rating.
         *
         * @return Rating.
         */
        public int getRating() {
            return this.rating;
        }

        /**
         * Position.
         *
         * @return Position.
         */
        public int getPosition() {
            return this.position;
        }
    }
}
