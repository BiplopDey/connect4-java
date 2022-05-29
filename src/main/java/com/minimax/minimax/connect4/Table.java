package com.minimax.minimax.connect4;

public class Table {
    //create a table
    public final int ROWS;
    public final int COLUMNS;
    private final char[][] table;
    //create constructor
    public Table(int row, int column){
        this.ROWS = row;
        this.COLUMNS = column;
        table = new char[row][column];
    }

    public Position get(int i, int j) {
        return new Position(i, j);
    }
    private boolean isValidPosition(int i, int j){
        return i >= 0 && i < ROWS && j >= 0 && j < COLUMNS;
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
            if(i < 0 || i >= ROWS || j < 0 || j >= COLUMNS)
                throw new IllegalArgumentException("Illegal position: " + i + "," + j);
            this.row = i;
            this.column = j;
            this.state = table[i][j];
        }

        public void placePlayer1(){
           placeToken(row, column, STATE.player1);
        }

        public void placePlayer2() {
            placeToken(row, column, STATE.player2);
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


        private void placeToken(int i, int j, Position.STATE state) {
            table[i][j] = state.getState();
        }
    }

}
