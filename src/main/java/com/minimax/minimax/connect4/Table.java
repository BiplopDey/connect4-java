package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode
public class Table {
    public final int ROWS;
    public final int COLUMNS;
    private final char[][] table;

    public Table(int rows, int columns){
        this.ROWS = rows;
        this.COLUMNS = columns;
        table = new char[rows][columns];

    }

    public Position get(int row, int column){
        if(!isValidPosition(row, column))
            throw new IllegalArgumentException("Illegal position: " + row + "," + column);
        return new Position(this).of(row, column);
    }

    private void placeAtColumn(int columnIndex, Position.STATE player) {
        if(!isValidColumn(columnIndex))
            throw new IllegalArgumentException("Illegal column: " + columnIndex);
        Column columnObj = getColumn(columnIndex);
        if(columnObj.isFull())
            throw new Column.ColumnFullException("Column " + columnIndex + " is full");
        columnObj.put(player);
    }

    public boolean isConnect4() {
        return getAllTableLists().stream().anyMatch(TableList::isConnect4);
    }

    public List<PositionPair> getPositionsOfConnect4() {
        if(!isConnect4())
            throw new IllegalStateException("No connect 4 found");

        return getAllTableLists().stream()
                .filter(TableList::isConnect4)
                .map(TableList::getPositionPair)
                .collect(Collectors.toList());
    }

    private List<TableList> getAllTableLists() {
        List<TableList> result = new ArrayList<>();
        result.addAll(getAllColumns());
        result.addAll(getAllRows());
     //   result.addAll(getAllDiagonals());
        return result;
    }

    public Column getColumn(int column) {
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);
        return new Column(column, this);
    }
    public Row getRow(int row) {
        if(!isValidRow(row))
            throw new IllegalArgumentException("Illegal row: " + row);
        return new Row(row, this);
    }
    private List<Column> getAllColumns() {
        return Column.getAll(this);
    }
/*
    private Diagonal getDiagonal(int column){
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);
        return new Diagonal(column, this);
    }

    private List<Diagonal> getAllDiagonals(){
        return IntStream.range(0, COLUMNS)
                .mapToObj(this::getDiagonal)
                .collect(Collectors.toList());
    }
*/

    private List<Row> getAllRows() {
        return Row.getAll(this);
    }

    public char[][] getTable() {
        return table;
    }

    public void placePlayer1AtColumn(int column) {
        placeAtColumn(column, Position.STATE.PLAYER_1);
    }

    public void placePlayer2AtColumn(int column) {
        placeAtColumn(column, Position.STATE.PLAYER_2);
    }

    public boolean isValidPosition(int i, int j){
        return isValidRow(i) && isValidColumn(j);
    }

    private boolean isValidColumn(int j){
        return j >= 0 && j < COLUMNS;
    }

    private boolean isValidRow(int row) {
        return row >= 0 && row < ROWS;
    }

    @Override
    public String toString() {
        String result = "";
        var rows = getAllRows();
        Collections.reverse(rows);
        for(Row row : rows)
            result += row.toString() + "\n";
        return result;
    }
}
