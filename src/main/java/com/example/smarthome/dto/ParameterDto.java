package com.example.smarthome.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor( access = AccessLevel.PROTECTED )
@Getter
@ToString
public class ParameterDto {
    private String type;
    private String value;
}
