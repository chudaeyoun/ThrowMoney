package com.kakaopay.throwing.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakaopay.throwing.domain.Throw;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ThrowDTO {
    private String token;
    private int user;
    private String room;
    private long money;
    private int count;

    public static Throw convertToEntity(ThrowDTO throwDTO) {
        return Throw.builder()
                .token(throwDTO.getToken())
                .user(throwDTO.getUser())
                .room(throwDTO.getRoom())
                .money(throwDTO.getMoney())
                .count(throwDTO.getCount())
                .build();
    }
}
