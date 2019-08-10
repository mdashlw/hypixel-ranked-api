package ru.mdashlw.hypixel.ranked.reply.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mdashlw.hypixel.ranked.entities.RankedPlayer;
import ru.mdashlw.hypixel.ranked.reply.Reply;

public class PlayerReply extends Reply<RankedPlayer> {
    @JsonProperty
    private RankedPlayer player;

    @Override
    public RankedPlayer getValue() {
        return player;
    }
}
