package com.PCThanhCong.forms;

import com.PCThanhCong.entity.ProductImage;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateProductImageForm {

    private String imageUrl;

    public ProductImage toEntity(){
        return new ProductImage(imageUrl);
    }

}
