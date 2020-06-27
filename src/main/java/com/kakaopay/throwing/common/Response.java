package com.kakaopay.throwing.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kakaopay.throwing.type.StatusCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private final StatusCode code;
    private final String message;
    private final T data;

    private Response(@NonNull StatusCode code, @NonNull String message, @Nullable T data) {
        this.code = code;
        this.message = Optional.of(message).orElse(code.getMessage());
        this.data = data;
    }

    public static <T> Response<T> ok(@Nullable T data) {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), data);
    }

    public static Response<String> ok() {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), "ok");
    }

    public static Response fail(@NonNull String message) {
        return new Response<>(StatusCode.FAIL, message, null);
    }
}