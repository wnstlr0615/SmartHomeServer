package com.example.smarthome.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TempTypeTest {
    @Test
    @DisplayName("[성공] fromValue 테스트")
    public void  givenTempValue_whenFromValue_thenReturnTempType (){
        //given
        //when
        //then

        assertAll(
                () -> assertThat(TempType.fromValue("온도")).isEqualTo(TempType.TEMPERATURE),
                () -> assertThat(TempType.fromValue("습도")).isEqualTo(TempType.HUMIDITY),
                () -> assertThat(TempType.fromValue("체감온도")).isEqualTo(TempType.SENSORY_TEMPERATURE)
        );
    }

}