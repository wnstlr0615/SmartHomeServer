package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Getter
public enum AirState {
    TURN_ON(List.of( "켜줘"), "켜다", "켜집니다."),
    TURN_OFF(List.of("꺼줘"), "끄다", "꺼집니다.")
    ;
    private final List<String> synonyms;
    private final String value;
    private final String responseMessage;

    public static AirState fromValue(String value){
        Optional<AirState> stateType = Arrays.stream(AirState.values())
                .filter(airState ->
                    airState.getSynonyms()
                                .stream()
                                .anyMatch(
                                        synonym -> synonym.equals(value)
                                )
                ).findFirst();
        return stateType.orElseThrow(
                () -> new IllegalArgumentException("잘못된 AirStateType 입니다.")
        );
    }
}
