package com.minimax.minimax.connect4.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row extends TableList {

    public Row(int row, Table table) {
        super(IntStream.range(0, table.COLUMNS)
                .mapToObj(column -> table.getPosition(row, column))
                .collect(Collectors.toList()));
    }

    public static List<Row> getAll(Table table) {
        return IntStream.range(0, table.ROWS)
                .mapToObj(row -> new Row(row, table))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return list.stream()
                .map(p->p.getState())
                .collect(Collectors.toList())
                .toString();
    }
}
