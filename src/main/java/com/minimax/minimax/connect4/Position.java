package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Position {
    enum STATE{
        EMPTY((char) 0){
            @Override
            public String toString() {
                return " ";
            }
        }, PLAYER_1('X'){
            @Override
            public String toString() {
                return "X";
            }
        }, PLAYER_2('O'){
            @Override
            public String toString() {
                return "O";
            }
        };

        private final char value;
        STATE(char state){
            this.value = state;
        }
        public char getValue(){
            return value;
        }
    }

    private final char[][] table;
    private final int row;
    private final int column;
    private STATE state;

    public Position(int row, int column, Table table){
        this.row = row;
        this.column = column;
        this.table = table.getTable();

        state = getState();
    }

    public STATE getState(){
        switch (table[row][column]){
            case 'X':
                return STATE.PLAYER_1;
            case 'O':
                return STATE.PLAYER_2;
            default:
                return STATE.EMPTY;
        }
    }

    public void placePlayer1(){
        place(STATE.PLAYER_1);
    }

    public void placePlayer2() {
        place(STATE.PLAYER_2);
    }

    public boolean isEmpty(){
        return state == STATE.EMPTY;
    }

    public boolean isPlayer1(){
        return state == STATE.PLAYER_1;
    }

    public boolean isPlayer2(){
        return state == STATE.PLAYER_2;
    }

    protected char getValue(){
        return table[row][column];
    }
    /*
    public void ensureIsValidPosition(){
        if(!table.isValidPosition(row, column))
            throw new IllegalArgumentException("Illegal position: " + row + "," + column);
    }
    */
    protected void place(Position.STATE state) {
        this.state = state;
        table[row][column] = state.getValue();
    }

    @Override
    public String toString() {
        return "Position(" + row + "," + column + ")";
    }
}