package com.example.smarthome.model;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LightStateTypeTest {

    @Test
    @Description("[성공] 룸타입 fromValue 테스트")
    public void givenValue_whenFromValue_thenReturnRoomType()  {

        //then
        assertAll(
                () -> assertThat(LightStateType.fromValue("켜줘"))
                        .isEqualTo(LightStateType.TURN_ON),
                () -> assertThat(LightStateType.fromValue("꺼줘"))
                        .isEqualTo(LightStateType.TURN_OFF),
                () -> assertThat(LightStateType.fromValue("불켜줘"))
                        .isEqualTo(LightStateType.TURN_ON),
                () -> assertThat(LightStateType.fromValue("불꺼줘"))
                        .isEqualTo(LightStateType.TURN_OFF)
        );
    }

    @Test
    @Description("[실패]룸타입 fromValue - RuntimeException 발생")
    public void givenValue_whenFromValue_thenRuntimeException() {
        //given
        String wrongValue = "불꺼";

        //when & then
        assertThrows(IllegalArgumentException.class,
                () -> LightStateType.fromValue(wrongValue)
        );

    }

}