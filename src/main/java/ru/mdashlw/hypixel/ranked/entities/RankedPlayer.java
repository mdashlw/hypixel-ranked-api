package ru.mdashlw.hypixel.ranked.entities;

/**
 * Ranked player.
 */
public class RankedPlayer {
    private String uuid;
    private String name;
    private String rank;
    private String guild;
    private int rating;
    private int position;

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
     * Player rank.
     *
     * @return Player rank or null.
     */
    public String getRank() {
        return rank;
    }

    /**
     * Player guild.
     *
     * @return Player guild or null.
     */
    public String getGuild() {
        return guild;
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
}
