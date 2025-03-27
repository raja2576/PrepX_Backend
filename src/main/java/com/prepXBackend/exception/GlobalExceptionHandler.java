package com.prepXBackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}



//{
//	  "fullName": "testuserame",
//	  "email": "test@example.commm",
//	  "password": "testpassword"
//	}


  

//
//{
//	  "email": "test@example.commm",
//	  "password": "testpassword"
//	}

