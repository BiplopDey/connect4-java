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
    @JsonProperty("row_size")
    public final int rowSize;

    public PlaceTokenRequest(List<List<Integer>> table, int column, int player, int rowSize) {
        this.column = column;
        this.player = player;
        this.table = table;
        this.rowSize = rowSize;
    }
}
