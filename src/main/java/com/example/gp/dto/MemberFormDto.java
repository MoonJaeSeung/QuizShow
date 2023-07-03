package com.example.gp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름에 공백은 불가합니다")
    private String nick;

    @NotEmpty(message = "필수적으로 입력해야합니다")
    private String password;
}
