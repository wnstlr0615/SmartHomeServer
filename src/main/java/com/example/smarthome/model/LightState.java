package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum LightState {
    TURN_ON(List.of("불켜줘", "켜줘"),"켜다", "켜집니다."),
    TURN_OFF(List.of("불꺼줘", "꺼줘"),"끄다", "꺼집니다.")
    ;
    private final List<String> synonyms;
    private final String value;
    private final String responseMessage;

    public static LightState fromValue(String value){
        Optional<LightState> lightStateType = Arrays.stream(LightState.values())
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
