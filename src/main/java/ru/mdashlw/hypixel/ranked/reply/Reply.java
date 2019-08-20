package ru.mdashlw.hypixel.ranked.reply;

public abstract class Reply<T> {
    private boolean success;
    private String cause;

    public abstract T getValue();

    public final boolean isSuccess() {
        return this.success;
    }

    public final String getCause() {
        return this.cause;
    }
}
