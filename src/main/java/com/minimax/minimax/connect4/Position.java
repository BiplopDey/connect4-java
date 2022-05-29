package com.minimax.minimax.connect4;
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

    private final char[][] table;
    private final int row;
    private final int column;
    public Position(int i, int j,  Table table){
        this.row = i;
        this.column = j;
        this.table = table.getTable();
    }

    public void placePlayer1(){
        place(STATE.player1);
    }

    public void placePlayer2() {
        place(STATE.player2);
    }

    public boolean isEmpty(){
        return table[row][column] == STATE.empty.getState();
    }

    public boolean isPlayer1(){
        return table[row][column] == STATE.player1.getState();
    }

    public boolean isPlayer2(){
        return table[row][column] == STATE.player2.getState();
    }

    protected void place(Position.STATE state) {
        table[row][column] = state.getState();
    }
}