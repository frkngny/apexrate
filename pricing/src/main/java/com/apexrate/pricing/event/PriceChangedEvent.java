package com.apexrate.pricing.event;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceChangedEvent {
    private Long productId;
    private String sku;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
}