package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum LightStateType {
    TURN_ON(List.of("불켜줘", "켜줘"),"켜다", "켜집니다."),
    TURN_OFF(List.of("불꺼줘", "꺼줘"),"끄다", "꺼집니다.")
    ;
    private final List<String> synonyms;
    private final String description;
    private final String responseMessage;

    public static LightStateType fromValue(String value){
        Optional<LightStateType> lightStateType = Arrays.stream(LightStateType.values())
                .filter(lightState ->
                        lightState.getSynonyms()
                                .stream()
                                .anyMatch(
                                        synonym -> synonym.equals(value)
                                )
                ).findFirst();
        return lightStateType.orElseThrow(
                () -> new IllegalArgumentException("잘못된 LightStateType 입니다.")
        );
    }

}
