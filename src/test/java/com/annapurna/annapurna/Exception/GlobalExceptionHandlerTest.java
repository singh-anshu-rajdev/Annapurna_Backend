package com.annapurna.annapurna.Exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {
    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleBaseException(Exception)}
     */
    @Test
    void testHandleBaseException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleBaseExceptionResult = globalExceptionHandler
                .handleBaseException(new CustomValidationException(ErrorCode.ERR_AP_2000));

        // Assert
        HttpStatusCode statusCode = actualHandleBaseExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleBaseExceptionResult.getBody();
        assertEquals("Internal Server Error", body.getMessage());
        assertEquals(2000, body.getErrorCode().intValue());
        assertEquals(412, actualHandleBaseExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.PRECONDITION_FAILED, statusCode);
        assertTrue(actualHandleBaseExceptionResult.hasBody());
        assertTrue(actualHandleBaseExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleExceptionResult = globalExceptionHandler
                .handleException(new Exception("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleExceptionResult.getBody();
        assertEquals("Internal Server Error", body.getMessage());
        assertEquals(2000, body.getErrorCode().intValue());
        assertEquals(500, actualHandleExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualHandleExceptionResult.hasBody());
        assertTrue(actualHandleExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleAccessDeniedException(Exception)}
     */
    @Test
    void testhandleRuntimeException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleAccessDeniedExceptionResult = globalExceptionHandler
                .handleAccessDeniedException(new Exception("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleAccessDeniedExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleAccessDeniedExceptionResult.getBody();
        assertEquals("Invalid Request Content", body.getMessage());
        assertEquals(2001, body.getErrorCode().intValue());
        assertEquals(403, actualHandleAccessDeniedExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.FORBIDDEN, statusCode);
        assertTrue(actualHandleAccessDeniedExceptionResult.hasBody());
        assertTrue(actualHandleAccessDeniedExceptionResult.getHeaders().isEmpty());
    }

    /**
     * Method under test:
     * {@link GlobalExceptionHandler#handleAccessDeniedException(Exception)}
     */
    @Test
    void testHandleAccessDeniedException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        // Act
        ResponseEntity<ErrorMessage> actualHandleAccessDeniedExceptionResult = globalExceptionHandler
                .handleAccessDeniedException(new Exception("foo"));

        // Assert
        HttpStatusCode statusCode = actualHandleAccessDeniedExceptionResult.getStatusCode();
        assertTrue(statusCode instanceof HttpStatus);
        ErrorMessage body = actualHandleAccessDeniedExceptionResult.getBody();
        assertEquals("Invalid Request Content", body.getMessage());
        assertEquals(2001, body.getErrorCode().intValue());
        assertEquals(403, actualHandleAccessDeniedExceptionResult.getStatusCodeValue());
        assertEquals(HttpStatus.FORBIDDEN, statusCode);
        assertTrue(actualHandleAccessDeniedExceptionResult.hasBody());
        assertTrue(actualHandleAccessDeniedExceptionResult.getHeaders().isEmpty());
    }
}
