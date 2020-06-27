package com.kakaopay.throwing.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    OK("success"),
    FAIL("error");

    private String message;
}
