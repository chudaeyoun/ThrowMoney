package com.kakaopay.throwing.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude
@ToString
public class ThrowDTO {
    private String token;
    private long money;
    private int count;
}
