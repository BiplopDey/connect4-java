package com.minimax.minimax.connect4.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Diagonal {
    public enum SLOPE {POSITIVE, NEGATIVE}
    private final Table table;

    public Diagonal(Table table) {
        this.table = table;
    }

    private List<Position> createList(Position position, SLOPE slope) {
        List<Position> result = new ArrayList<>();
        int row = position.getRow();
        int column = position.getColumn();
        int rowIncrement = slope == SLOPE.POSITIVE ? 1 : -1;

        while(table.isValidPosition(row, column)){
            result.add(table.getPosition(row, column++));
            row += rowIncrement;
        }

        return result;
    }

    public TableList getPositiveSlope(int row, int column) {
        return getPositiveSlope(table.getPosition(row, column));
    }

    public TableList getNegativeSlope(int row, int column) {
        return getNegativeSlope(table.getPosition(row, column));
    }

    private TableList getPositiveSlope(Position initialPosition) {
        return new TableList(createList(initialPosition, SLOPE.POSITIVE));
    }

    private TableList getNegativeSlope(Position initialPosition) {
        return new TableList(createList(initialPosition, SLOPE.NEGATIVE));
    }

    public List<TableList> getAll() {
        List<TableList> diagonals = new ArrayList<>();
        diagonals.addAll(getAllPositiveSlope());
        diagonals.addAll(getAllNegativeSlope());
        return diagonals;
    }

    public List<TableList> getAllPositiveSlope() {
        return getInitialPositiveSlopePositions().stream()
                .map(this::getPositiveSlope)
                .collect(Collectors.toList());
    }

    private List<Position> getInitialPositiveSlopePositions() {
        List<Position> result = new ArrayList<>();

        var reverseOrderColumns = table
                .getColumn(0)
                .getPositions();
        Collections.reverse(reverseOrderColumns);

        result.addAll(reverseOrderColumns);
        result.addAll(table
                .getRow(0)
                .getPositions()
                .subList(1, table.COLUMNS));

        return result;
    }

    public List<TableList> getAllNegativeSlope() {
        return getInitialNegativeSlopePositions().stream()
                .map(this::getNegativeSlope)
                .collect(Collectors.toList());
    }

    private List<Position> getInitialNegativeSlopePositions() {
        List<Position> result = new ArrayList<>();

        result.addAll(table
                .getColumn(0)
                .getPositions());
        result.addAll(table
                .getRow(table.ROWS-1)
                .getPositions()
                .subList(1, table.COLUMNS));

        return result;
    }

}
