package com.minimax.minimax.connect4.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Diagonal {
    public enum SLOPE {POSITIVE, NEGATIVE}
    private final Table table;

    public Diagonal(Table table) {
        this.table = table;
    }

    public List<TableList> getAll() {
        List<TableList> diagonals = new ArrayList<>();
        diagonals.addAll(getAllPositiveSlope());
        diagonals.addAll(getAllNegativeSlope());
        return diagonals;
    }

    protected TableList getPositiveSlope(int row, int column) {
        return getPositiveSlope(table.getCell(row, column));
    }

    protected TableList getNegativeSlope(int row, int column) {
        return getNegativeSlope(table.getCell(row, column));
    }

    private TableList getPositiveSlope(Cell initialCell) {
        return new TableList(createList(initialCell, SLOPE.POSITIVE));
    }

    private TableList getNegativeSlope(Cell initialCell) {
        return new TableList(createList(initialCell, SLOPE.NEGATIVE));
    }

    private List<Cell> createList(Cell cell, SLOPE slope) {
        List<Cell> result = new ArrayList<>();
        int row = cell.getRow();
        int column = cell.getColumn();
        int rowIncrement = slope == SLOPE.POSITIVE ? 1 : -1;

        while(table.isValidPosition(row, column)){
            result.add(table.getCell(row, column++));
            row += rowIncrement;
        }

        return result;
    }

    public List<TableList> getAllPositiveSlope() {
        return getInitialPositiveSlopeCells().stream()
                .map(this::getPositiveSlope)
                .collect(Collectors.toList());
    }

    private List<Cell> getInitialPositiveSlopeCells() {
        var reverseOrderColumns = getFirstColumnCells();
        Collections.reverse(reverseOrderColumns);
        List<Cell> result = new ArrayList<>(reverseOrderColumns);
        int firstRow = 0;
        result.addAll(getRowCellsExcludingFirstElement(firstRow));

        return result;
    }

    public List<TableList> getAllNegativeSlope() {
        return getInitialNegativeSlopeCells().stream()
                .map(this::getNegativeSlope)
                .collect(Collectors.toList());
    }

    private List<Cell> getInitialNegativeSlopeCells() {
        List<Cell> result = new ArrayList<>(getFirstColumnCells());
        int lastRow = table.getRowSize() - 1;
        result.addAll(getRowCellsExcludingFirstElement(lastRow));

        return result;
    }

    private List<Cell> getFirstColumnCells() {
        return IntStream.range(0, table.getRowSize())
                .mapToObj(row -> table.getCell(row, 0))
                .collect(Collectors.toList());
    }

    private List<Cell> getRowCellsExcludingFirstElement(int row) {
        return table.getRow(row).getCells().subList(1, table.getColumnSize());
    }
}
