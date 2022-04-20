package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum TempType {
    TEMPERATURE(List.of("온도"), "온도", "온도"),
    SENSORY_TEMPERATURE(List.of("체감온도"), "체감온도", "체감온도"),
    HUMIDITY(List.of("습도"), "습도", "습도")
    ;
    private final List<String> synonyms;
    private final String description;
    private final String responseMessage;

    public static TempType fromValue(String value){
        Optional<TempType> tempType = Arrays.stream(TempType.values())
                .filter(type ->
                        type.getSynonyms()
                                .stream()
                                .anyMatch(
                                        synonym -> synonym.equals(value)
                                )
                ).findFirst();

        return tempType.orElseThrow(
                () -> new IllegalArgumentException("잘못된 TempType 입니다.")
        );
    }
}
