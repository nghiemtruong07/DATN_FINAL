package com.PCThanhCong.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateCategoryDTO {
    @NotBlank
    private String name;
}
