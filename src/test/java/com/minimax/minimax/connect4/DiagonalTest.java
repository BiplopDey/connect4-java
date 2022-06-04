package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiagonalTest {

    @Test
    void diagonal_positive_slope_of_position_5_0_has_size_1() {
        var table = new Table(6,7);
        var position = new Position( table).of(5,0);
        var diagonal = new Diagonal(position, Diagonal.SLOPE.POSITIVE);
        assertEquals(1,diagonal.getList().size());
        assertEquals(position,diagonal.getList().get(0));
    }

    @Test
    @Disabled
    void diagonal_positive_slope_of_position_5_1_has_size_2() {
        var table = new Table(6,7);
        var position = new Position( table).of(5,0);
        var diagonal = new Diagonal(position, Diagonal.SLOPE.POSITIVE);
        assertEquals(1,diagonal.getList().size());
        assertEquals(position,diagonal.getList().get(0));
    }
}