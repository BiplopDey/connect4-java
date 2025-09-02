package com.minimax.minimax.connect4.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Column extends TableList {
    private final int column;
    private final Table table;
    public Column(int column, Table table){
        super(IntStream.range(0, table.getRowSize())
                .mapToObj(row -> table.getCell(row, column))
                .filter(cell -> !cell.isEmpty())
                .collect(Collectors.toList()));
        this.column = column;
        this.table = table;
    }

    public boolean isFull() {
        return size() == table.getRowSize();
    }

    public void put(Player player) {
        if(isFull())
            throw new ColumnFullException("Column "+ column +" is full");

        var position = table.getCell(size(), column);
        position.place(player);
        list.add(position);
    }

    public static List<Column> getAll(Table table) {
        return IntStream.range(0, table.getColumnSize())
                .mapToObj(column -> new Column(column, table))
                .collect(Collectors.toList());
    }

}
