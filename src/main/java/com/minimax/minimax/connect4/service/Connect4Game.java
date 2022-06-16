package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.Connect4Table;

public interface Connect4Game {
    Connect4Table placePlayer1AtColumn(int column);
}
