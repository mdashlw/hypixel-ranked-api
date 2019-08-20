package ru.mdashlw.hypixel.ranked.reply.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mdashlw.hypixel.ranked.entities.Game;
import ru.mdashlw.hypixel.ranked.reply.Reply;

public final class GameReply extends Reply<Game> {
    @JsonProperty
    private Game game;

    @Override
    public Game getValue() {
        return this.game;
    }
}
