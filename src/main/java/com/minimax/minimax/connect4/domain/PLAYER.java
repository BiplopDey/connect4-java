package com.minimax.minimax.connect4.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Player {
    PLAYER_1('X') {
        @Override
        public String toString() {
            return "X";
        }
    },
    PLAYER_2('O') {
        @Override
        public String toString() {
            return "O";
        }
    };

    private final char value;
}
