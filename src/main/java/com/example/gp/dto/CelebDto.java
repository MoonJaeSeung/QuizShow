package com.example.gp.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.*;
@Getter
@Setter
@ToString
public class CelebDto {

    @NotBlank(message = "연예인 이름을 입력해주세요")
    private String name;

    private String fileName;

    private String fileOriName;

    private String fileUrl;

}
