package com.minimax.minimax.connect4.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PlaceTokenRequest {
    @JsonProperty("table")
    public final List<List<Integer>> table;
    @JsonProperty("player")
    public final int player;
    @JsonProperty("column")
    public final int column;

    public PlaceTokenRequest( List<List<Integer>> table, int column, int player) {
        this.column = column;
        this.player = player;
        this.table = table;
    }
}
