package com.minimax.minimax.connect4.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Column extends TableList {
    private final int column;
    private final Table table;
    public Column(int column, Table table){
        //list = [X, O, X, , , , ,]
        super(IntStream.range(0, table.ROWS)
                .mapToObj(row -> table.getPosition(row, column))
                .filter(position -> position.getState() != Position.STATE.EMPTY)
                .collect(Collectors.toList()));
        this.column = column;
        this.table = table;
    }

    public boolean isFull() {
        return size() == table.ROWS;
    }

    public Column put(Position.STATE state) {
        if(isFull())
            throw new ColumnFullException("Column "+ column +" is full");

        var position = table.getPosition(size(), column);
        position.place(state);
        list.add(position);
        return this;
    }

    public static List<Column> getAll(Table table) {
        return IntStream.range(0, table.COLUMNS)
                .mapToObj(column -> new Column(column, table))
                .collect(Collectors.toList());
    }

}
