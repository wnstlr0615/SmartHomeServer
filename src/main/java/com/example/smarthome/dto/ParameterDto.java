package com.example.smarthome.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@Builder
@ToString
public class ParameterDto {
    private String type;
    private String value;

    public static ParameterDto createParameterDto( String type, String value) {
        return ParameterDto.builder()
                .type(type)
                .value(value)
                .build();
    }
}
