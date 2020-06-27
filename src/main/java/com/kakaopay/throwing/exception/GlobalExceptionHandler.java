package com.kakaopay.throwing.exception;

import com.kakaopay.throwing.common.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> handleValidException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(e.getMessage()));
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Response> handleIndexOutBoundsException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail("null point error"));
    }
}
