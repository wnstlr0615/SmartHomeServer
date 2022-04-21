package com.example.smarthome.error.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GlobalErrorCode implements ErrorCode{
    INTERNAL_SERVER_ERROR("서버에 알 수 없는 문제가 발생 하였습니다.", 500);
    private final String description;
    private final int statusCode;
}
