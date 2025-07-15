package com.minimax.minimax.connect4.domain;

import java.util.ArrayList;
import java.util.List;


public class CellPair {
    private final List<Cell> list;

    public CellPair(Cell cell, Cell cell1) {
        list = new ArrayList<>(2);
        list.add(cell);
        list.add(cell1);
    }

    public Cell getFirst() {
        return list.get(0);
    }

    public Cell getSecond() {
        return list.get(1);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPair that = (CellPair) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
