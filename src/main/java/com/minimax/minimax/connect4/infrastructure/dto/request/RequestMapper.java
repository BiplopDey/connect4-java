package com.minimax.minimax.connect4.infrastructure.dto.request;

import com.minimax.minimax.connect4.domain.Connect4Table;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {
    private final int EMPTY_CELL = 0;
    private final int PLAYER_1_CELL = 1;
    private final int PLAYER_2_CELL = 2;

    public Connect4Table mapToConnect4Table(PlaceTokenRequest request) {
        var tableMatrix = request.table;
        ensureIsValidTable(tableMatrix);
        if (!(0 < request.column && request.column <= tableMatrix.get(0).size()))
            throw new IllegalArgumentException("Invalid column");
        if(!isValidPlayer(request.player))
            throw new IllegalArgumentException("Invalid player");
        return null;
    }

    private void ensureIsValidTable(List<List<Integer>> table) {
        if(!areAllRowsTheSameLength(table))
            throw new IllegalArgumentException("Invalid table row size");
        if(!isValidTableContent(table))
            throw new IllegalArgumentException("Invalid table content");
        if(!isValidTableColumnsPosition(table))
            throw new IllegalArgumentException("Invalid table columns position");
    }

    private boolean isValidTableColumnsPosition(List<List<Integer>> table) {
        for (int i = 0; i < getColumnSize(table); i++)
            if (!areValidColumnPositions(getColumn(table, i)))
                return false;

        return true;
    }

    private int getColumnSize(List<List<Integer>> table) {
        return table.get(0).size();
    }

    private List<Integer> getColumn(List<List<Integer>> table, int column) {
        return table.stream()
                .map(row -> row.get(column))
                .collect(Collectors.toList());
    }

    private boolean areValidColumnPositions(List<Integer> column) {
        if (!column.contains(EMPTY_CELL))
            return true;

        int lastEmptyCellIndex = column.lastIndexOf(EMPTY_CELL);
        return column.subList(lastEmptyCellIndex + 1, column.size())
                .contains(EMPTY_CELL);
    }

    private boolean isValidTableContent(List<List<Integer>> table) {
        for(List<Integer> sublist : table)
            for(Integer cell : sublist)
                if(!isValidCell(cell))
                    return false;

        return true;
    }

    private boolean isValidCell(int cell) {
        return cell == EMPTY_CELL || cell == PLAYER_1_CELL || cell == PLAYER_2_CELL;
    }
    private boolean areAllRowsTheSameLength(List<List<Integer>> table) {
        return table.stream().mapToInt(List::size).distinct().count() == 1;
    }

    private boolean isValidPlayer(int player) {
        return player == PLAYER_1_CELL || player == PLAYER_2_CELL;
    }
}
