package com.minimax.minimax.connect4.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minimax.minimax.connect4.domain.PLAYER;
import com.minimax.minimax.connect4.service.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"table", "status", "winner", "cells_of_connect4"})
public class Connect4GameResponse {
    @JsonProperty("table")
    private final List<List<Integer>> table;
    @Setter
    @JsonProperty("status")
    private Game.STATUS status;
    @JsonProperty("winner")
    private PLAYER winner;
    @JsonProperty("cells_of_connect4")
    private List<List<List<Integer>>> cellsOfConnect4;

    public Connect4GameResponse(List<List<Integer>> table) {
        this.table = table;
    }

    public Connect4GameResponse setCellsOfConnect4(List<List<List<Integer>>> cellsOfConnect4) {
        this.cellsOfConnect4 = cellsOfConnect4;
        return this;
    }

    public Connect4GameResponse setWinner(PLAYER winner) {
        this.winner = winner;
        return this;
    }

}
