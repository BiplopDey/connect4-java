package com.minimax.minimax.connect4.domain;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Table implements Connect4Table {
    private final int rowSize;
    private final int columnSize;
    private char[][] table;

    public Table(int rowSize, List<List<Player>> table) {
        this.columnSize = table.size();
        this.rowSize = rowSize;
        if (!areAllColumnsSizeLEQThanROW_SIZE(table))
            throw new IllegalArgumentException("All columns length should be less than row size specified");

        buildTable(table);
    }

    public Table(int rowSize, int columnSize) {
        this.rowSize = rowSize;
        this.columnSize = columnSize;
        initializeTable();
    }

    private void initializeTable() {
        table = new char[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++)
            for (int j = 0; j < columnSize; j++)
                table[i][j] = Cell.EMPTY;
    }

    private void buildTable(List<List<Player>> table) {
        initializeTable();
        for (int columnIndex = 0; columnIndex < columnSize; columnIndex++)
            for (int row = 0; row < table.get(columnIndex).size(); row++)
                this.table[row][columnIndex] = table.get(columnIndex).get(row).getValue();
    }

    public Cell getCell(int row, int column) {
        ensureIsValidCell(row, column);
        return new Cell(this).of(row, column);
    }

    private void ensureIsValidCell(int row, int column) {
        ensureIsValidRow(row);
        ensureIsValidColumn(column);
    }

    @Override
    public boolean isConnect4() {
        return getAllTableLists().stream().anyMatch(TableList::isConnect4);
    }

    @Override
    public List<CellPair> getCellsOfConnect4() {
        if (!isConnect4())
            throw new IllegalStateException("No connect 4 found");

        return getAllTableLists().stream()
                .filter(TableList::isConnect4)
                .map(TableList::getCellPair)
                .toList();
    }

    @Override
    public boolean isFull() {
        return getAllColumns().stream().allMatch(Column::isFull);
    }

    private List<TableList> getAllTableLists() {
        List<TableList> result = new ArrayList<>();

        result.addAll(getAllColumns());
        result.addAll(getAllRows());
        result.addAll(getAllDiagonals());

        return result;
    }

    public List<Column> getAllColumns() {
        return Column.getAll(this);
    }

    public Column getColumn(int column) {
        ensureIsValidColumn(column);
        return new Column(column, this);
    }

    private void ensureIsValidColumn(int column) {
        if (!isValidColumn(column))
            throw new InvalidColumnIndexException("Illegal column: " + column);
    }

    private List<TableList> getAllDiagonals() {
        return new Diagonal(this).getAll();
    }

    private List<Row> getAllRows() {
        return Row.getAll(this);
    }

    public Row getRow(int row) {
        ensureIsValidRow(row);
        return new Row(row, this);
    }

    private void ensureIsValidRow(int row) {
        if (!isValidRow(row))
            throw new IllegalArgumentException("Illegal row: " + row);
    }

    @Override
    public void placePlayer1AtColumn(int column) {
        placeAtColumn(column, Player.PLAYER_1);
    }

    @Override
    public void placePlayer2AtColumn(int column) {
        placeAtColumn(column, Player.PLAYER_2);
    }

    private void placeAtColumn(int columnIndex, Player player) {
        ensureIsValidColumn(columnIndex);
        getColumn(columnIndex).put(player);
    }

    public boolean isValidPosition(int i, int j) {
        return isValidRow(i) && isValidColumn(j);
    }

    private boolean isValidColumn(int j) {
        return j >= 0 && j < columnSize;
    }

    private boolean isValidRow(int row) {
        return row >= 0 && row < rowSize;
    }

    private boolean areAllColumnsSizeLEQThanROW_SIZE(List<List<Player>> table) {
        return table.stream().mapToInt(List::size).allMatch(colSize -> colSize <= rowSize);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        var rows = getAllRows();
        for (int i = rows.size() - 1; i >= 0; i--) {
            result.append(rows.get(i).toString());
            result.append("\n");
        }
        return result.toString();
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    @Override
    public char[][] getTable() {
        char[][] copy = new char[rowSize][columnSize];
        for (int i = 0; i < rowSize; i++) {
            System.arraycopy(table[i], 0, copy[i], 0, columnSize);
        }
        return copy;
    }

    char getAt(int row, int column) {
        return table[row][column];
    }

    void setAt(int row, int column, char value) {
        table[row][column] = value;
    }
}
