package com.redis_app.controller.v1.request;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest implements Serializable {

    private String title;
    private String description;
    private BigDecimal price;
    private Integer count;
}
