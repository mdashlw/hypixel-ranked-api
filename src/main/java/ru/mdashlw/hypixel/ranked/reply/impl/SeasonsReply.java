package ru.mdashlw.hypixel.ranked.reply.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mdashlw.hypixel.ranked.entities.RankedSeason;
import ru.mdashlw.hypixel.ranked.reply.Reply;

import java.util.List;

public class SeasonsReply extends Reply<List<RankedSeason>> {
    @JsonProperty
    private List<RankedSeason> seasons;

    @Override
    public List<RankedSeason> getValue() {
        return seasons;
    }
}
