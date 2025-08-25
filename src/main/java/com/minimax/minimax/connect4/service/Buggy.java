package com.minimax.minimax.connect4.service;

public class Buggy {
    public String upper(String s) {
        return s.toUpperCase(); // posible NPE -> bug en muchas reglas
    }
}

