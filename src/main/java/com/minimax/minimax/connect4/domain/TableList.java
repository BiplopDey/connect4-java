package com.minimax.minimax.connect4.domain;

import java.util.Arrays;
import java.util.List;

public class TableList {
    protected final List<Cell> list;
    private CellPair cellPair;

    public TableList(List<Cell> list) {
        this.list = list;
    }

    private boolean areConnected(Cell... cells) {
        return Arrays.stream(cells).allMatch(Cell::isPlayer1) || Arrays.stream(cells).allMatch(Cell::isPlayer2);
    }

    public boolean isConnect4() {
        for(int i = 0; i < list.size() - 3; i++) {
            Cell p1 = list.get(i);
            Cell p2 = list.get(i + 1);
            Cell p3 = list.get(i + 2);
            Cell p4 = list.get(i + 3);
            if (areConnected(p1, p2, p3, p4)){
                cellPair = new CellPair(p1, p4);
                return true;
            }
        }
        return false;
    }

    public CellPair getCellPair() {
        if(!isConnect4())
            throw new IllegalStateException("Column is not connect4");
        return cellPair;
    }

    public List<Cell> getCells() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public Cell get(int i) {
        return list.get(i);
    }

    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableList that = (TableList) o;
        return list.equals(that.list);
    }
}
