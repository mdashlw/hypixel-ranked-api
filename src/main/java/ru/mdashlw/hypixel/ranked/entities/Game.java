package ru.mdashlw.hypixel.ranked.entities;

/**
 * Network game stats.
 */
public final class Game {
    private String type;
    private String mode;
    private String map;
    private String winner;

    /**
     * Game type, e.g. SkyWars.
     *
     * @return Game type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Game mode, e.g. RankedInsane.
     *
     * @return Game mode.
     */
    public String getMode() {
        return this.mode;
    }

    /**
     * Game map.
     *
     * @return Game map.
     */
    public String getMap() {
        return this.map;
    }

    /**
     * Game winner.
     *
     * @return Game winner.
     */
    public String getWinner() {
        return this.winner;
    }
}
