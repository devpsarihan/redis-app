package com.redis_app.model.dto;

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
public class ProductDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer count;
    private Long createdDate;
    private Long modifiedDate;
}
