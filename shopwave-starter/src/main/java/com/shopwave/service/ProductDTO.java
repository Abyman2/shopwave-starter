//ATE/6743/14
package com.shopwave.service;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId
) {}