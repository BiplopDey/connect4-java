package com.minimax.minimax.connect4.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Cell {
    public static final char EMPTY = ' ';
    private final Table table;
    private Integer row;
    private Integer column;

    public Cell(Table table){
        this.table = table;
    }

    private Cell(Integer row, Integer column, Table table){
        this.table = table;
        this.row = row;
        this.column = column;
    }

    public Cell of(int row, int column){
        if(!table.isValidPosition(row, column))
            throw new InvalidPositionException("Illegal position: " + row + "," + column);
        return new Cell(row, column, table);
    }

    public void placePlayer1(){
        place(PLAYER.PLAYER_1);
    }

    public void placePlayer2() {
        place(PLAYER.PLAYER_2);
    }

    public boolean isEmpty(){
        return !isPlayer1() && !isPlayer2();
    }

    public boolean isPlayer1(){
        return getValue() == PLAYER.PLAYER_1.getValue();
    }

    public boolean isPlayer2(){
        return getValue() == PLAYER.PLAYER_2.getValue();
    }

    protected char getValue(){
        ensureRowAndColumnAreNotNull();
        return table.getTable()[row][column];
    }

    protected void place(PLAYER player) {
        ensureRowAndColumnAreNotNull();
        table.getTable()[row][column] = player.getValue();
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