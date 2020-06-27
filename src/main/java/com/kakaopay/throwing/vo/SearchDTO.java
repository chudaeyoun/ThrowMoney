package com.kakaopay.throwing.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SearchDTO {
    private String token;
    private LocalDateTime createAt;
    private long throwingMoney;
    private long receivedMoney;
    private int user;
    private List<ReceiveDTO> receiveDTOList;
}
