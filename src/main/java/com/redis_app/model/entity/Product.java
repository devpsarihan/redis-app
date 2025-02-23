package com.redis_app.model.entity;

import com.redis_app.model.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "PRODUCT", name = "PRODUCT")
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "COUNT")
    private Integer count;

    public ProductDto toProductDto() {
        return ProductDto.builder()
            .id(this.getId())
            .title(this.title)
            .description(this.description)
            .price(this.price)
            .count(this.count)
            .createdDate(this.getCreatedDate().toInstant(ZoneOffset.UTC).toEpochMilli())
            .modifiedDate(this.getModifiedDate().toInstant(ZoneOffset.UTC).toEpochMilli())
            .build();
    }
}
