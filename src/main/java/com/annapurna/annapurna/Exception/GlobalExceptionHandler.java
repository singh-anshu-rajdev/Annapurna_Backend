package com.annapurna.annapurna.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.rmi.AccessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Method CustomValidationException
     *
     * @param ex the Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<ErrorMessage> handleBaseException(Exception ex){
        ex.printStackTrace();
        LOGGER.error("Handle Base Exception - {}",ex);
        CustomValidationException validationException = (CustomValidationException) ex; // converting into Exception class
        ErrorHandle error = validationException.getErrorCode(); // Fetching error data
        ErrorMessage errorMessage = new ErrorMessage(); // setting user readable Error Message
        errorMessage.setErrorCode(error.getErrorCode());
        errorMessage.setMessage(error.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.PRECONDITION_FAILED);
    }

    /**
     * Method handleRuntimeException
     *
     * @param ex the Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(Exception ex){
        ex.printStackTrace();
        LOGGER.error("handleRuntimeException - {}",ex);
        ErrorHandle error = ErrorCode.ERR_AP_2000;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(error.getErrorCode());
        errorMessage.setMessage(error.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method handleException
     *
     * @param ex the Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex){
        ex.printStackTrace();
        LOGGER.error("handleException - {}",ex);
        ErrorHandle error = ErrorCode.ERR_AP_2000;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(error.getErrorCode());
        errorMessage.setMessage(error.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method handleAccessDeniedException
     *
     * @param ex the Exception
     * @return ResponseEntity
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(Exception ex){
        ex.printStackTrace();
        LOGGER.error("handleAccessDeniedException - {}",ex);
        ErrorHandle error = ErrorCode.ERR_AP_2001;
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(error.getErrorCode());
        errorMessage.setMessage(error.getMessage());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.FORBIDDEN);
    }

}
