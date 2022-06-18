package com.minimax.minimax.connect4.infrastructure.dto.response;

import com.minimax.minimax.connect4.domain.Cell;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class CellPairResponse {
    private final List<Integer> list;

    public CellPairResponse(Integer cell, Integer cell1) {
        list = new ArrayList<>(2);
        list.add(cell);
        list.add(cell1);
    }
}
