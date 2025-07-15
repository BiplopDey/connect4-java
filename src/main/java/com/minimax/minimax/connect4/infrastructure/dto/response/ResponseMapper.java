package com.minimax.minimax.connect4.infrastructure.dto.response;

import com.minimax.minimax.connect4.domain.Cell;
import com.minimax.minimax.connect4.domain.CellPair;
import com.minimax.minimax.connect4.domain.Column;
import com.minimax.minimax.connect4.domain.Connect4Table;
import com.minimax.minimax.connect4.service.Connect4Game;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseMapper {
    private final int PLAYER_1_CELL = 1;
    private final int PLAYER_2_CELL = 2;

    public Connect4GameResponse mapConnect4Game(Connect4Game game) {
        Connect4Table table = game.getTable();
        var allColumns = table.getAllColumns().stream()
                .map(this::mapColumnToListInteger)
                .collect(Collectors.toList());

        var result = new Connect4GameResponse(allColumns);
        result.setStatus(game.getStatus());
        if (game.getStatus() == Connect4Game.STATUS.WINNER)
            result
                    .setCellsOfConnect4(mapCellsOfConnect4(game.getCellsOfConnect4()))
                    .setWinner(game.getWinner());


        return result;
    }

    private List<List<List<Integer>>> mapCellsOfConnect4(List<CellPair> cellsOfConnect4) {
        return cellsOfConnect4.stream()
                .map(this::mapCellPairToListInteger)
                .collect(Collectors.toList());
    }

    private List<List<Integer>> mapCellPairToListInteger(CellPair cellPair) {
        return List.of(
                mapCellToPoint(cellPair.getFirst()),
                mapCellToPoint(cellPair.getSecond()));
    }

    private List<Integer> mapCellToPoint(Cell cell) {
        return List.of(cell.getRow(), cell.getColumn());
    }

    private List<Integer> mapColumnToListInteger(Column column) {
        return column.getCells().stream()
                .map(this::mapCellPlayerToInteger)
                .collect(Collectors.toList());
    }

    private Integer mapCellPlayerToInteger(Cell cell) {
        return cell.isPlayer1() ? PLAYER_1_CELL : PLAYER_2_CELL;
    }
}
