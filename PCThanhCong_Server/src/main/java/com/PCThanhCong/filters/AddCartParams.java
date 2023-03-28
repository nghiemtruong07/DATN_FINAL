package com.PCThanhCong.filters;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AddCartParams {

    private Integer userId;
    private Integer productId;
    private Integer Amount;

}
