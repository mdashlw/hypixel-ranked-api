package ru.mdashlw.hypixel.ranked.entities;

/**
 * Ranked leaderboard player.
 */
public final class LeaderboardPlayer {
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

    /**
     * Ranked wins.
     *
     * @return Ranked wins.
     */
    public int getWins() {
        return this.wins;
    }

    /**
     * Ranked kills.
     *
     * @return Ranked kills.
     */
    public int getKills() {
        return this.kills;
    }
}
