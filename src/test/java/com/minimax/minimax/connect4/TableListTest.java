package com.minimax.minimax.connect4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableListTest {

    @Test
    void is_not_connect4_when_list_size_is_less_than_4() {
        var table = new Table(4,4);

        var sut = new TableList(List.of(table.getPosition(0,0), table.getPosition(1,1), table.getPosition(2,2)));

        assertFalse(sut.isConnect4());

        var sut1 = new TableList(List.of(table.getPosition(1,1), table.getPosition(2,2)));
        assertFalse(sut1.isConnect4());
    }
}