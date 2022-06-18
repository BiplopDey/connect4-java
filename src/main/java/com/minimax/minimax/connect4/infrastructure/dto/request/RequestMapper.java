package com.minimax.minimax.connect4.infrastructure.dto.request;

import com.minimax.minimax.connect4.domain.Connect4Table;
import com.minimax.minimax.connect4.domain.PLAYER;
import com.minimax.minimax.connect4.domain.Table;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequestMapper {
    private final int PLAYER_1_CELL = 1;
    private final int PLAYER_2_CELL = 2;

    public Connect4Table mapToConnect4Table(PlaceTokenRequest request) {
        var table = request.table;

        if(!isValidPlayer(request.player))
            throw new IllegalArgumentException("Invalid player");
        if(!areAllCellsValid(table))
            throw new IllegalArgumentException("Invalid cell value");
        
        return new Table(request.rowSize, mapRequestTable(table));
    }

    private List<List<PLAYER>> mapRequestTable(List<List<Integer>> table) {
        return table.stream()
                .map(row -> row.stream()
                        .map(this::getPlayer)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private boolean areAllCellsValid(List<List<Integer>> table) {
        return table.stream()
                .flatMap(List::stream)
                .allMatch(this::isValidCell);
    }

    private boolean isValidCell(int cell) {
        return cell == PLAYER_1_CELL || cell == PLAYER_2_CELL;
    }

    public PLAYER getPlayer(int player) {
        if(player == PLAYER_1_CELL)
            return PLAYER.PLAYER_1;
        if(player == PLAYER_2_CELL)
            return PLAYER.PLAYER_2;

        throw new IllegalArgumentException("Invalid player");
    }

    private boolean isValidPlayer(int player) {
        return player == PLAYER_1_CELL || player == PLAYER_2_CELL;
    }
}
