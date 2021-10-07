package com.example.demo.model.response;

import com.example.demo.model.enu.Code;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private int status;
    private Code code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> validationMessages;
    private Object data;
    private Timestamp timestamp;
    private String lang;

}
