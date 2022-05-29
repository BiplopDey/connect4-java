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

    public Position get(int i, int j) {
        if(!isValidPosition(i,j))
            throw new IllegalArgumentException("Illegal position: " + i + "," + j);
        return new Position(i, j);
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

    public class Position{
        enum STATE{
            empty((char) 0), player1('X'), player2('O');
            private final char state;
            STATE(char state){
                this.state = state;
            }
            public char getState(){
                return state;
            }
        }

        private final char state;
        private final int row;
        private final int column;
        public Position(int i, int j) {
            this.row = i;
            this.column = j;
            this.state = table[i][j];
        }

        public void placePlayer1(){
           place(STATE.player1);
        }

        public void placePlayer2() {
            place(STATE.player2);
        }

        public boolean isEmpty(){
            return state == STATE.empty.getState();
        }

        public boolean isPlayer1(){
            return state == STATE.player1.getState();
        }

        public boolean isPlayer2(){
            return state == STATE.player2.getState();
        }

        private void place(Position.STATE state) {
            table[row][column] = state.getState();
        }
    }


}
