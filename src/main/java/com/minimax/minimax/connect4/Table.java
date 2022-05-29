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

    private boolean isValidPosition(int i, int j){
        return i >= 0 && i < ROWS && isValidColumn(j);
    }

    private boolean isValidColumn(int j){
        return j >= 0 && j < COLUMNS;
    }

    public void placePlayer1AtColumn(int column) {
        placeAtColumn(column, Position.STATE.player1);
    }
    public void placePlayer2AtColumn(int column) {
        placeAtColumn(column, Position.STATE.player2);
    }

    private void placeAtColumn(int column, Position.STATE player) {
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);

        for(int row = 0; row < ROWS; row++)
            if(get(row, column).isEmpty()) {
                get(row, column).place(player);
                return;
            }

        throw new IllegalArgumentException("Column " + column + " is full");
    }
    /*
    // get a particular column
    public Column getColumn(int column) {
        if(!isValidColumn(column))
            throw new IllegalArgumentException("Illegal column: " + column);

        Column col = new Column();
        for(int row = 0; row < ROWS; row++)
            col.add(get(row, column));
        return col;
    }
    */
    public boolean isConnect4() {
        return true;
    }

    //get table
    public char[][] getTable() {
        return table;
    }

}
