package com.example.smarthome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum RoomType {
    LIVING_ROOM(List.of("거실등", "거실"),"거실등", "거실등"),
    KITCHEN_ROOM(List.of("주방등", "주방"),"주방등", "주방등"),
    MY_ROOM(List.of("내방", "내방등"),"내방등", "내방등"),
    ALL_ROOM(List.of("전체", "전체등"),"전체등", "전체등")
    ;
    private final List<String> synonyms;
    private final String description;
    private final String responseMessage;

    public static RoomType fromValue(String value){
        Optional<RoomType> findRoomType = Arrays.stream(RoomType.values())
                .filter(roomType ->
                        roomType.getSynonyms()
                                .stream()
                                .anyMatch(
                                        synonym -> synonym.equals(value)
                                )
                ).findFirst();

        return findRoomType.orElseThrow(
                () -> new IllegalArgumentException("잘못된 RoomType 입니다.")
        );
    }

}
