package ru.mdashlw.hypixel.ranked.entities;

/**
 * Ranked leaderboard player.
 */
public class LeaderboardPlayer {
    private String uuid;
    private String name;
    private int rating;
    private int position;
    private int wins;
    private int kills;

    /**
     * Undashed UUID.
     *
     * @return Undashed UUID.
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Player name.
     *
     * @return Player name.
     */
    public String getName() {
        return name;
    }

    /**
     * Rating.
     *
     * @return Rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Position.
     *
     * @return Position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Ranked wins.
     *
     * @return Ranked wins.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Ranked kills.
     *
     * @return Ranked kills.
     */
    public int getKills() {
        return kills;
    }
}
