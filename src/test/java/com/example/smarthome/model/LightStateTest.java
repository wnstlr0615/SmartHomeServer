package com.example.smarthome.model;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LightStateTest {

    @Test
    @Description("[성공] 룸타입 fromValue 테스트")
    public void givenValue_whenFromValue_thenReturnRoomType()  {

        //then
        assertAll(
                () -> assertThat(LightState.fromValue("켜줘"))
                        .isEqualTo(LightState.TURN_ON),
                () -> assertThat(LightState.fromValue("꺼줘"))
                        .isEqualTo(LightState.TURN_OFF),
                () -> assertThat(LightState.fromValue("불켜줘"))
                        .isEqualTo(LightState.TURN_ON),
                () -> assertThat(LightState.fromValue("불꺼줘"))
                        .isEqualTo(LightState.TURN_OFF)
        );
    }

    @Test
    @Description("[실패]룸타입 fromValue - RuntimeException 발생")
    public void givenValue_whenFromValue_thenRuntimeException() {
        //given
        String wrongValue = "불꺼";

        //when & then
        assertThrows(IllegalArgumentException.class,
                () -> LightState.fromValue(wrongValue)
        );

    }

}