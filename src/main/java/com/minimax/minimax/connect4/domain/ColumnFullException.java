package com.minimax.minimax.connect4.domain;

public class ColumnFullException extends IllegalStateException {
    public ColumnFullException(String message) {
        super(message);
    }
}