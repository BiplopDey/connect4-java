package com.minimax.minimax.connect4;

import java.util.List;

public class Diagonal extends TableList{
    public Diagonal(Position position, SLOPE slope) {
        super(List.of(position));
    }

    public enum SLOPE {
        POSITIVE,
        NEGATIVE
    }

}
