package com.minimax.minimax.connect4.service;

public class BadQualityExample {

    // Constantes que no siguen la convención
    public static int MAGIC_NUMBER = 42;

    // Variable pública (mala práctica)
    public String data;

    // Método demasiado largo y con código duplicado
    public void process(String input) {
        if (input.equals("A")) {
            System.out.println("Processing A");
            System.out.println("Processing A");
            System.out.println("Processing A"); // duplicación
        } else if (input.equals("B")) {
            System.out.println("Processing B");
            System.out.println("Processing B");
            System.out.println("Processing B"); // duplicación
        } else {
            if (input == null) { // posible NPE
                input.toString();
            }
            System.out.println("Other case: " + input);
        }
    }

    // Método sin usar
    private void unusedMethod() {
        System.out.println("Nunca me llaman");
    }

    // Método con demasiados parámetros
    public void badMethod(int a, int b, int c, int d, int e, int f) {
        System.out.println(a + b + c + d + e + f);
    }

    // Falta de documentación, comentarios inútiles
    // TODO arreglar algún día
}
