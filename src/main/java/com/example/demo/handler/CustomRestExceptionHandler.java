package com.example.demo.handler;

import com.example.demo.exception.CustomException;
import com.example.demo.model.enu.Code;
import com.example.demo.model.response.ResponseData;
import com.example.demo.util.ResponsePreProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomRestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    @Autowired
    private ResponsePreProcessor responsePreProcessor;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> handleAllOtherException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        logger.info(ex.getMessage(), ex);
        return responsePreProcessor.buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, Code.UNKNOWN_ERROR, null);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseData> handleCustomException(CustomException ex, WebRequest request) {
        return responsePreProcessor.buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getErrorCode(), null);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseData> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        ResponseData responseData = responsePreProcessor.buildResponseData(HttpStatus.METHOD_NOT_ALLOWED.value(), Code.METHOD_NOT_SUPPORT, null);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" " + responseData.getMessage());
        ex.getSupportedHttpMethods().forEach(t -> builder.append(" " + t));
        responseData.setMessage(builder.toString());
        return responsePreProcessor.buildResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        ResponseData responseData = responsePreProcessor.buildResponseData(HttpStatus.BAD_REQUEST.value(), Code.VALIDATION_FAILED, null);
        List<String> validationMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            validationMessages.add(error.getDefaultMessage());
        }
        responseData.setValidationMessages(validationMessages);
        return responsePreProcessor.buildResponseEntity(HttpStatus.BAD_REQUEST, responseData);
    }

}
