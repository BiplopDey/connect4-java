package com.minimax.minimax.connect4;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Diagonal extends TableList{
    public enum SLOPE {POSITIVE, NEGATIVE}

    public Diagonal(Position position, SLOPE slope) {
       super(new ArrayList<>());
       list.addAll(createList(position, slope));
    }

    private List<Position> createList(Position position, SLOPE slope) {
        List<Position> list = new ArrayList<>();
        var table = position.getTable();
        int row = position.getRow();
        int column = position.getColumn();
        int rowIncrement = slope == SLOPE.POSITIVE ? 1 : -1;

        while (table.isValidPosition(row, column)){
            list.add(table.get(row, column));
            row += rowIncrement;
            column++;
        }

        return list;
    }

    public static List<Diagonal> getAllPositiveSlope(Table table) {
        return getInitialPositiveSlopePositions(table).stream()
                .map(p -> new Diagonal(p, SLOPE.POSITIVE))
                .collect(Collectors.toList());
    }

    private static List<Position> getInitialPositiveSlopePositions(Table table) {
        List<Position> result = new ArrayList<>();

        var reverseOrderColumns = table
                .getColumn(0)
                .getList();
        Collections.reverse(reverseOrderColumns);
        result.addAll(reverseOrderColumns);
        result.addAll(table
                .getRow(0)
                .getList()
                .subList(1, table.COLUMNS));

        return result;
    }

    public static List<Diagonal> getAllNegativeSlope(Table table) {
        return getInitialNegativeSlopePositions(table).stream()
                .map(p -> new Diagonal(p, SLOPE.NEGATIVE))
                .collect(Collectors.toList());
    }

    private static List<Position> getInitialNegativeSlopePositions(Table table) {
        List<Position> result = new ArrayList<>();

        result.addAll(table
                .getColumn(0)
                .getList());
        result.addAll(table
                .getRow(table.ROWS-1)
                .getList()
                .subList(1, table.COLUMNS));

        return result;
    }

}
