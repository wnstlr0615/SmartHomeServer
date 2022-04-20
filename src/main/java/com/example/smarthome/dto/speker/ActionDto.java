package com.example.smarthome.dto.speker;

import com.example.smarthome.dto.ParameterDto;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ActionDto {
    @NotBlank(message = "actionName 은 필수 입니다.")
    private String actionName;
    @NotNull(message = "parameters는 null일 수 없습니다.")
    private Map<String, ParameterDto> parameters;

    //== 생성 메서드 ==//
    public static ActionDto createActionDto(String actionName, Map<String, ParameterDto> parameters) {
        return ActionDto.builder()
                .actionName(actionName)
                .parameters(parameters)
                .build();
    }
}
