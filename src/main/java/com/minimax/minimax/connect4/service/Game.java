package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.Connect4Table;

public class Game implements Connect4Game {
    private final Connect4Table table;

    public Game(Connect4Table table) {
        this.table = table;
    }

    public Connect4Table placePlayer1AtColumn(int i) {
        return null;
    }
}
