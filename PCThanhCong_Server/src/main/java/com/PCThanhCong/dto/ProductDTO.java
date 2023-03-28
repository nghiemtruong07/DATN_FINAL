package com.PCThanhCong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor  
@AllArgsConstructor
public class ProductDTO {
    @NotBlank
    @Length(min = 20)
    private String title;

    @NotBlank
    @Length(max = 500)
    private String descriptions;

    private Integer originalPrice;

    private Integer promotionPrice;

    private Integer categoryId;

    private Integer amount;

    private List<ProductImagesDTO> productImages;
    @Data
    @NoArgsConstructor
    static class ProductImagesDTO{
        @NotBlank
        private String imageUrl;
    }
}

