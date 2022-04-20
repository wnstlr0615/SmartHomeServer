package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum TvState {
    TURN_ON(List.of( "켜줘"), "켜다", "켤게요."),
    TURN_OFF(List.of("꺼줘"), "끄다", "끌게요.")
    ;
    private final List<String> synonyms;
    private final String value;
    private final String responseMessage;

    public static TvState fromValue(String value){
        Optional<TvState> stateType = Arrays.stream(TvState.values())
                .filter(state ->
                        state.getSynonyms()
                                .stream()
                                .anyMatch(
                                        synonym -> synonym.equals(value)
                                )
                ).findFirst();
        return stateType.orElseThrow(
                () -> new IllegalArgumentException("잘못된 OnOffState 입니다.")
        );
    }
}
