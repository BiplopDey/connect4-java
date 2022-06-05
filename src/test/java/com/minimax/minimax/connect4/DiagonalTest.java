package com.minimax.minimax.connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalTest {
    Position initialPosition;
    Table table6x7, table3x3;

    @BeforeEach
    void setUp() {
        table6x7 = new Table(6, 7);
        table3x3 = new Table(3, 3);
    }

    @Test
    void diagonal_positive_slope_of_position_5_0() {
        initialPosition = table6x7.get(5,0);

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.POSITIVE);

        assertEquals(1, diagonal.getList().size());
        assertEquals(initialPosition, diagonal.getList().get(0));
    }

    @Test
    void diagonal_negative_slope_of_position_5_6() {
        initialPosition = table6x7.get(5,6);

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.NEGATIVE);

        assertEquals(1, diagonal.getList().size());
        assertEquals(initialPosition, diagonal.getList().get(0));
    }

    @Test
    void diagonal_positive_slope_of_position_4_0() {
        initialPosition = table6x7.get(4,0);

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.POSITIVE);

        assertEquals(List.of(initialPosition, table6x7.get(5,1)) , diagonal.getList());
    }

    @Test
    void diagonal_negative_slope_of_position_5_5() {
        initialPosition = table6x7.get(5,5);

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.NEGATIVE);

        assertEquals(List.of(initialPosition, table6x7.get(4,6)) , diagonal.getList());
    }

    @Test
    void diagonal_positive_slope_of_position_0_0() {
        initialPosition = table3x3.get(0,0);
        List<Position> list = List.of(initialPosition, table3x3.get(1,1), table3x3.get(2,2));

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.POSITIVE);

        assertEquals(list , diagonal.getList());
    }

    @Test
    void diagonal_negative_slope_of_position_2_0() {
        initialPosition = table3x3.get(2,0);
        List<Position> list = List.of(initialPosition, table3x3.get(1,1), table3x3.get(0,2));

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.NEGATIVE);

        assertEquals(list , diagonal.getList());
    }

    @Test
    void diagonal_positive_slope_of_position_0_1() {
        initialPosition = table3x3.get(0,1);
        List<Position> list = List.of(initialPosition, table3x3.get(1,2));

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.POSITIVE);

        assertEquals(list , diagonal.getList());
    }

    @Test
    void diagonal_positive_slope_of_position_1_0() {
        initialPosition = table3x3.get(1,0);
        List<Position> list = List.of(initialPosition, table3x3.get(0,1));

        var diagonal = new Diagonal(initialPosition, Diagonal.SLOPE.NEGATIVE);

        assertEquals(list , diagonal.getList());
    }

    @Test
    void get_all_positive_slope_diagonal(){
        var sut = Diagonal.getAllPositiveSlope(table3x3);

        assertEquals(5, sut.size());
        assertEquals(table3x3.get(2,0), sut.get(0).getList().get(0));

        assertEquals(table3x3.get(0,0), sut.get(2).getList().get(0));
        assertEquals(table3x3.get(1,1), sut.get(2).getList().get(1));

        assertEquals(table3x3.get(0,2), sut.get(4).getList().get(0));
    }

    @Test
    void get_all_negative_slope_diagonal(){
        var sut = Diagonal.getAllNegativeSlope(table3x3);

        assertEquals(5, sut.size());
        assertEquals(table3x3.get(2,2), sut.get(4).getList().get(0));

        assertEquals(table3x3.get(2,0), sut.get(2).getList().get(0));
        assertEquals(table3x3.get(1,1), sut.get(2).getList().get(1));

        assertEquals(table3x3.get(0,0), sut.get(0).getList().get(0));
    }


}