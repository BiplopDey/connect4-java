package com.minimax.minimax.connect4;

public class Table {
    public final int ROWS;
    public final int COLUMNS;
    private final char[][] table;

    public Table(int row, int column){
        this.ROWS = row;
        this.COLUMNS = column;
        table = new char[row][column];
    }

    public Position get(int row, int column){
        if(!isValidPosition(row, column))
            throw new IllegalArgumentException("Illegal position: " + row + "," + column);
        return new Position(row, column, this);
    }



    private void placeAtColumn(int column, Position.STATE player) {
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);
        Column columnObj = getColumn(column);
        if(columnObj.isFull())
            throw new IllegalArgumentException("Column " + column + " is full");
        updateColumn(column, columnObj.put(player));
    }

    private void updateColumn(int column, Column columnObj) {
        for(int row = 0; row < ROWS; row++)
            table[row][column] = columnObj.getList().get(row).getValue();
    }

    private Column getColumn(int column) {
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);
        return new Column(column, this);
    }

    public boolean isConnect4() {
        for(int column = 0; column < COLUMNS; column++)
            if(getColumn(column).isConnect4())
                    return true;
        return false;
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

    private boolean isValidPosition(int i, int j){
        return i >= 0 && i < ROWS && isValidColumn(j);
    }

    private boolean isValidColumn(int j){
        return j >= 0 && j < COLUMNS;
    }
}
