package com.minimax.minimax.connect4;

import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.PostMapping;

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

    private final Table table;
    private Integer row;
    private Integer column;

    public Position(Table table){
        this.table = table;
    }

    private Position( Integer row, Integer column, Table table){
        this.table = table;
        this.row = row;
        this.column = column;
    }

    public Position of(int row, int column){
        if(!table.isValidPosition(row, column))
            throw new InvalidPositionException("Illegal position: " + row + "," + column);

        return new Position(row, column, table);
    }

    public STATE getState(){
        ensureRowAndColumnAreNotNull();

        return switch (table.getTable()[row][column]) {
            case 'X' -> STATE.PLAYER_1;
            case 'O' -> STATE.PLAYER_2;
            default -> STATE.EMPTY;
        };
    }

    public void placePlayer1(){
        place(STATE.PLAYER_1);
    }

    public void placePlayer2() {
        place(STATE.PLAYER_2);
    }

    public boolean isEmpty(){
        return getState() == STATE.EMPTY;
    }

    public boolean isPlayer1(){
        return getState() == STATE.PLAYER_1;
    }

    public boolean isPlayer2(){
        return getState() == STATE.PLAYER_2;
    }

    protected char getValue(){
        ensureRowAndColumnAreNotNull();
        return table.getTable()[row][column];
    }

    protected void place(Position.STATE state) {
        ensureRowAndColumnAreNotNull();
        table.getTable()[row][column] = state.getValue();
    }

    @Override
    public String toString() {
        return "Position(" + row + "," + column + ")";
    }

    public void ensureRowAndColumnAreNotNull(){
        if(row == null || column == null)
            throw new IllegalStateException("Position cannot be null");
    }

    public static class InvalidPositionException extends RuntimeException{
        public InvalidPositionException(String message){
            super(message);
        }
    }
}