package com.minimax.minimax.connect4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Column extends TableList {

    public Column(int column, Table table){
        super(IntStream.range(0, table.ROWS)
                .mapToObj(row -> table.getPosition(row, column))
                .collect(Collectors.toList()));
    }

    public boolean isFull() {
        return list.stream().noneMatch(Position::isEmpty);
    }

    public Column put(Position.STATE state) {
        for(Position p : list)
            if(p.isEmpty()) {
                p.place(state);
                return this;
            }

        throw new ColumnFullException("Column "+ list.get(0).getColumn() +" is full");
    }

    public static List<Column> getAll(Table table) {
        return IntStream.range(0, table.COLUMNS)
                .mapToObj(column -> new Column(column, table))
                .collect(Collectors.toList());
    }

    public static class ColumnFullException extends RuntimeException {
        public ColumnFullException(String message) {
            super(message);
        }
    }
}
