package com.example.demo.model.enu;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Code {

    SUCCESS("success"),
    FAIL("fail"),
    UNKNOWN_ERROR("unknown.error"),
    METHOD_NOT_SUPPORT("method.not.support"),
    VALIDATION_FAILED("validation.failed"),
    USER_ID_EXISTED("user.id.existed"),
    USER_ID_NOT_EXISTED("user.id.not.existed"),
    ;

    private final String description;

}
