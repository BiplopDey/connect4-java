package com.minimax.minimax.connect4.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalTest {
    Table table6x7, table3x3;
    Diagonal diagonalTable6x7;
    Diagonal diagonalTable3x3;

    @BeforeEach
    void setUp() {
        table6x7 = new Table(6, 7);
        table3x3 = new Table(3, 3);

        diagonalTable6x7 = new Diagonal(table6x7);
        diagonalTable3x3 = new Diagonal(table3x3);
    }

    @Test
    void diagonal_positive_slope_of_position_5_0() {
        TableList sut = diagonalTable6x7.getPositiveSlope(5,0);

        assertEquals(1, sut.size());
        assertEquals(table6x7.getCell(5,0), sut.get(0));
    }

    @Test
    void diagonal_negative_slope_of_position_5_6() {
        var sut = diagonalTable6x7.getNegativeSlope(5,6);

        assertEquals(1, sut.size());
        assertEquals(table6x7.getCell(5,6), sut.get(0));
    }

    @Test
    void diagonal_positive_slope_of_position_4_0() {
        var sut = diagonalTable6x7.getPositiveSlope(4,0);

        assertEquals(List.of(table6x7.getCell(4,0), table6x7.getCell(5,1)) , sut.getCells());
    }

    @Test
    void diagonal_negative_slope_of_position_5_5() {
        var sut = diagonalTable6x7.getNegativeSlope(5,5);

        assertEquals(List.of(table6x7.getCell(5,5), table6x7.getCell(4,6)) , sut.getCells());
    }

    @Test
    void diagonal_positive_slope_of_position_0_0() {
        List<Cell> list = List.of(table3x3.getCell(0,0), table3x3.getCell(1,1), table3x3.getCell(2,2));

        var sut = diagonalTable3x3.getPositiveSlope(0,0);

        assertEquals(list , sut.getCells());
    }

    @Test
    void diagonal_negative_slope_of_position_2_0() {
        List<Cell> list = List.of(table3x3.getCell(2,0), table3x3.getCell(1,1), table3x3.getCell(0,2));

        var sut = diagonalTable3x3.getNegativeSlope(2,0);

        assertEquals(list , sut.getCells());
    }

    @Test
    void diagonal_positive_slope_of_position_0_1() {
        List<Cell> list = List.of(table3x3.getCell(0,1), table3x3.getCell(1,2));

        var sut = diagonalTable3x3.getPositiveSlope(0,1);

        assertEquals(list , sut.getCells());
    }

    @Test
    void diagonal_positive_slope_of_position_1_0() {
        List<Cell> list = List.of(table3x3.getCell(1,0), table3x3.getCell(2,1));

        var sut = diagonalTable3x3.getPositiveSlope(1,0);

        assertEquals(list , sut.getCells());
    }

    @Test
    void get_all_positive_slope_diagonal(){
        var sut = diagonalTable3x3.getAllPositiveSlope();

        assertEquals(5, sut.size());
        assertEquals(table3x3.getCell(2,0), sut.get(0).getCells().get(0));

        assertEquals(table3x3.getCell(0,0), sut.get(2).getCells().get(0));
        assertEquals(table3x3.getCell(1,1), sut.get(2).getCells().get(1));

        assertEquals(table3x3.getCell(0,2), sut.get(4).getCells().get(0));
    }

    @Test
    void get_all_negative_slope_diagonal(){
        var sut = diagonalTable3x3.getAllNegativeSlope();

        assertEquals(5, sut.size());
        assertEquals(table3x3.getCell(2,2), sut.get(4).getCells().get(0));

        assertEquals(table3x3.getCell(2,0), sut.get(2).getCells().get(0));
        assertEquals(table3x3.getCell(1,1), sut.get(2).getCells().get(1));

        assertEquals(table3x3.getCell(0,0), sut.get(0).getCells().get(0));
    }

    @Test
    void get_all_diagonals(){
        var sut = diagonalTable3x3.getAll();

        assertEquals(10, sut.size());
    }
}