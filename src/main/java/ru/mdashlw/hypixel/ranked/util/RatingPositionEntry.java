package ru.mdashlw.hypixel.ranked.util;

public class RatingPositionEntry {
    private int rating;
    private int position;

    public RatingPositionEntry(int rating, int position) {
        this.rating = rating;
        this.position = position;
    }

    /**
     * Stored rating.
     *
     * @return Stored rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Stored position.
     *
     * @return Stored position.
     */
    public int getPosition() {
        return position;
    }
}
