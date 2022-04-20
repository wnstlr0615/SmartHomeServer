package com.example.smarthome.model;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RoomTypeTest {

    @Test
    @Description("[성공] 룸타입 fromValue 테스트")
    public void givenValue_whenFromValue_thenReturnRoomType(){
        // given & when
        RoomType livingRoom = RoomType.fromValue("거실등");
        RoomType kitchenRoom = RoomType.fromValue("주방");
        RoomType allRoom = RoomType.fromValue("전체등");
        RoomType myRoom = RoomType.fromValue("내방");

        //then
        assertAll(
                () -> assertThat(livingRoom).isNotNull(),
                () -> assertThat(kitchenRoom).isNotNull(),
                () -> assertThat(allRoom).isNotNull(),
                () -> assertThat(myRoom).isNotNull()
        );
    }

    @Test
    @Description("[실패]룸타입 fromValue - RuntimeException 발생")
    public void givenValue_whenFromValue_thenRuntimeException() {
        //given
        String wrongRoomValue = "잘못된 방";

        //when & then
        assertThrows(IllegalArgumentException.class,
                 () -> RoomType.fromValue(wrongRoomValue)
         );

    }
}