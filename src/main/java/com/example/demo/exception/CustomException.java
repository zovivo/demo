package com.example.demo.exception;

import com.example.demo.model.enu.Code;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends Exception {

    private Code errorCode;

}
