package com.cs2trade.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {


    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        NotFoundException ex = new NotFoundException("Not here");
        ResponseEntity<String> response = handler.handleNotFound(ex);
        assertEquals(404, response.getStatusCode().value());
        assertEquals("Not here", response.getBody());
    }

    @Test
    void testHandleValidation() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        var bindingResult = mock(org.springframework.validation.BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new ObjectError("obj", "Must not be null")));

        ResponseEntity<String> response = handler.handleValidation(ex);
        assertEquals(400, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Must not be null"));
    }

    @Test
    void testHandleOther() {
        Exception ex = new Exception("Unexpected error");
        ResponseEntity<String> response = handler.handleOther(ex);
        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error: Unexpected error", response.getBody());
    }

}