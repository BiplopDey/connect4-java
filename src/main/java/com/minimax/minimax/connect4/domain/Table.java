package com.minimax.minimax.connect4.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Table implements Connect4Table {
    public final int ROW_SIZE;
    public final int COLUMN_SIZE;
    @Getter
    private  char[][] table;

    public Table(int rowSize, List<List<PLAYER>> table) {
        this.COLUMN_SIZE = table.size();
        this.ROW_SIZE = rowSize;
        if(!areAllColumnsSizeLEQThanROW_SIZE(table))
            throw new IllegalArgumentException("All columns length should be less than row size specified");

        buildTable(table);
    }

    public Table(int rowSize, int columnSize){
        ROW_SIZE = rowSize;
        COLUMN_SIZE = columnSize;
        initializeTable();
    }

    private void initializeTable() {
        table = new char[ROW_SIZE][COLUMN_SIZE];
        for(int i = 0; i < ROW_SIZE; i++)
            for(int j = 0; j < COLUMN_SIZE; j++)
                table[i][j] = Cell.EMPTY;
    }

    private void buildTable(List<List<PLAYER>> table){
        initializeTable();
        for (int columnIndex = 0; columnIndex < COLUMN_SIZE; columnIndex++)
            for (int row = 0; row < table.get(columnIndex).size(); row++)
                this.table[row][columnIndex] = table.get(columnIndex).get(row).getValue();
    }

    public Cell getCell(int row, int column){
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
        if(!isConnect4())
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
        return  new Column(column, this);
    }

    private void ensureIsValidColumn(int column) {
        if(!isValidColumn(column))
            throw new InvalidColumnIndexException("Illegal column: " + column);
    }

    private List<TableList> getAllDiagonals(){
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
        if(!isValidRow(row))
            throw new IllegalArgumentException("Illegal row: " + row);
    }

    @Override
    public void placePlayer1AtColumn(int column) {
        placeAtColumn(column, PLAYER.PLAYER_1);
    }

    @Override
    public void placePlayer2AtColumn(int column) {
        placeAtColumn(column, PLAYER.PLAYER_2);
    }

    private void placeAtColumn(int columnIndex, PLAYER player) {
        ensureIsValidColumn(columnIndex);
        getColumn(columnIndex).put(player);
    }

    public boolean isValidPosition(int i, int j){
        return isValidRow(i) && isValidColumn(j);
    }

    private boolean isValidColumn(int j){
        return j >= 0 && j < COLUMN_SIZE;
    }

    private boolean isValidRow(int row) {
        return row >= 0 && row < ROW_SIZE;
    }

    private boolean areAllColumnsSizeLEQThanROW_SIZE(List<List<PLAYER>> table) {
        return table.stream().mapToInt(List::size).allMatch(colSize -> colSize <= ROW_SIZE) ;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        var rows = getAllRows();
        for(int i = rows.size() - 1; i >= 0; i--){
            result.append(rows.get(i).toString());
            result.append("\n");
        }
        return result.toString();
    }
}
