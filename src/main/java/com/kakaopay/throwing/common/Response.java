package com.kakaopay.throwing.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakaopay.throwing.type.StatusCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private final StatusCode code;
    private final String message;

    private Response(@NonNull StatusCode code, @NonNull String message) {
        this.code = code;
        this.message = Optional.of(message).orElse(code.getMessage());
    }

    public static Response ok() {
        return new Response(StatusCode.OK, StatusCode.OK.getMessage());
    }

    public static Response fail(@NonNull String message) {
        return new Response(StatusCode.FAIL, message);
    }
}