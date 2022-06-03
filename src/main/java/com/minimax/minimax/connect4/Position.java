package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Position{
    enum STATE{
        EMPTY((char) 0), PLAYER_1('X'), PLAYER_2('O');
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
    public Position(int row, int column, Table table){
        this.row = row;
        this.column = column;
        this.table = table.getTable();
    }

    public void placePlayer1(){
        place(STATE.PLAYER_1);
    }

    public void placePlayer2() {
        place(STATE.PLAYER_2);
    }

    public boolean isEmpty(){
        return table[row][column] == STATE.EMPTY.getState();
    }

    public boolean isPlayer1(){
        return table[row][column] == STATE.PLAYER_1.getState();
    }

    public boolean isPlayer2(){
        return table[row][column] == STATE.PLAYER_2.getState();
    }

    protected char getValue(){
        return table[row][column];
    }

    protected void place(Position.STATE state) {
        table[row][column] = state.getState();
    }

    @Override
    public String toString() {
        return "Position(" + row + "," + column + ")";
    }
}