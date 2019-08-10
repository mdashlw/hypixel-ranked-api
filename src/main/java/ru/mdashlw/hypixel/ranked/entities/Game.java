package ru.mdashlw.hypixel.ranked.entities;

/**
 * Network game stats.
 */
public class Game {
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
        return type;
    }

    /**
     * Game mode, e.g. RankedInsane.
     *
     * @return Game mode.
     */
    public String getMode() {
        return mode;
    }

    /**
     * Game map.
     *
     * @return Game map.
     */
    public String getMap() {
        return map;
    }

    /**
     * Game winner.
     *
     * @return Game winner.
     */
    public String getWinner() {
        return winner;
    }
}
