package com.cs2trade.exception;

import com.cs2trade.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotFoundExceptionTest {

    @Test
    void testExceptionMessage() {
        NotFoundException ex = new NotFoundException("Not found");
        assertEquals("Not found", ex.getMessage());
    }

}