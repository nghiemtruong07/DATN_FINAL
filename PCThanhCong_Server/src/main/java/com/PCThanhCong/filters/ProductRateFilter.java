package com.PCThanhCong.filters;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRateFilter {
    private Integer page;

    private Integer perPage;

    private Integer productId;
}
