package com.minimax.minimax.connect4.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Row extends TableList {

    public Row(int row, Table table) {
        super(
                IntStream.range(0, table.COLUMN_SIZE)
                .mapToObj(column -> table.getCell(row, column))
                .toList()
        );
    }

    public static List<Row> getAll(Table table) {
        return IntStream.range(0, table.ROW_SIZE)
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
