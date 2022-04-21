package com.example.smarthome.error;

import com.example.smarthome.error.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public static ErrorResponse fromErrorCode(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMessage(errorCode.getDescription())
                .build();
    }
}
