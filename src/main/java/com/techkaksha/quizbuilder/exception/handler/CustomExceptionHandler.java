package com.techkaksha.quizbuilder.exception.handler;


import com.techkaksha.quizbuilder.exception.SizeConstraintException;
import com.techkaksha.quizbuilder.exception.error.ApiError;
import com.techkaksha.quizbuilder.exception.EntityNotFoundException;
import com.techkaksha.quizbuilder.exception.URLNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.net.ConnectException;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConnectException.class)
    protected ResponseEntity<Object> handleJDBCConnectionRefuseException(
            ConnectException ex
    ){
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(SizeConstraintException.class)
    protected ResponseEntity<Object> handleSizeRelatedException(
            SizeConstraintException ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(URLNotFoundException.class)
    protected ResponseEntity<Object> handleURLNotFound(
            URLNotFoundException ex
    ){
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
