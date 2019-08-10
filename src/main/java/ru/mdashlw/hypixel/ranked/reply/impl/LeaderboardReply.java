package ru.mdashlw.hypixel.ranked.reply.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mdashlw.hypixel.ranked.entities.LeaderboardPlayer;
import ru.mdashlw.hypixel.ranked.reply.Reply;

import java.util.List;

public class LeaderboardReply extends Reply<List<LeaderboardPlayer>> {
    @JsonProperty
    private List<LeaderboardPlayer> leaderboard;

    @Override
    public List<LeaderboardPlayer> getValue() {
        return leaderboard;
    }
}
