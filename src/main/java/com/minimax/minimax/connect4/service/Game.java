package com.minimax.minimax.connect4.service;

import com.minimax.minimax.connect4.domain.CellPair;
import com.minimax.minimax.connect4.domain.Connect4Table;
import com.minimax.minimax.connect4.domain.PLAYER;
import lombok.Getter;

import java.util.List;

public class Game implements Connect4Game {
    @Getter
    private final Connect4Table table;

    public Game(Connect4Table table) {
        this.table = table;
    }

    public void place(PLAYER player, int column) {
        if(player == PLAYER.PLAYER_1){
            table.placePlayer1AtColumn(column-1);
            return;
        }
        table.placePlayer2AtColumn(column-1);
    }

    @Override
    public PLAYER getWinner() {
        var winnerCells = table.getCellsOfConnect4();
        var cell = winnerCells.get(0).getFirst();
        return cell.isPlayer1() ? PLAYER.PLAYER_1 : PLAYER.PLAYER_2;
    }

    public STATUS getStatus() {
        if(table.isConnect4())
            return STATUS.WINNER;
        if(table.isFull())
            return STATUS.DRAW;
        return STATUS.PLAYING;
    }

    @Override
    public List<CellPair> getCellsOfConnect4() {
        return table.getCellsOfConnect4();
    }
}
