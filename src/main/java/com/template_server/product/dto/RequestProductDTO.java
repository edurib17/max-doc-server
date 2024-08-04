package com.template_server.product.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductDTO {
    private String id;
    @Column(length = 12)
    private String cod;
    @Column(length = 100)
    private String name;
    private Double quantity;
    private BigDecimal price;
}
