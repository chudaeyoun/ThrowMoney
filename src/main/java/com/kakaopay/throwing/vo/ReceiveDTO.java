package com.kakaopay.throwing.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ReceiveDTO {
    private String token;
    private int user;
    private String room;
    private long money;
}
