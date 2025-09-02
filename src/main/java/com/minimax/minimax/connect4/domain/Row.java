package com.minimax.minimax.connect4.domain;

import java.util.List;
import java.util.stream.IntStream;

public class Row extends TableList {

    public Row(int row, Table table) {
        super(
                IntStream.range(0, table.getColumnSize())
                        .mapToObj(column -> table.getCell(row, column))
                        .toList()
        );
    }

    public static List<Row> getAll(Table table) {
        return IntStream.range(0, table.getRowSize())
                .mapToObj(row -> new Row(row, table))
                .toList();
    }

    @Override
    public String toString() {
        return list.stream()
                .map(Cell::getValue)
                .toList()
                .toString();
    }


}
