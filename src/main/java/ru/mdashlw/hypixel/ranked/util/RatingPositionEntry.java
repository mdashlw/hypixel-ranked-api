package ru.mdashlw.hypixel.ranked.util;

public final class RatingPositionEntry {
    private final int rating;
    private final int position;

    public RatingPositionEntry(final int rating, final int position) {
        this.rating = rating;
        this.position = position;
    }

    /**
     * Stored rating.
     *
     * @return Stored rating.
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Stored position.
     *
     * @return Stored position.
     */
    public int getPosition() {
        return this.position;
    }
}
