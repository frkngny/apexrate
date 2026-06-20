package com.apexrate.pricing.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.apexrate.pricing.entity.Product;
import com.apexrate.pricing.event.PriceChangedEvent;
import com.apexrate.pricing.messaging.PriceEventProducer;
import com.apexrate.pricing.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final ProductRepository productRepository;
    private final PriceEventProducer priceEventProducer;

    @Transactional
    public Product updatePriceDynamically(String sku, double demandFactor) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with SKU: " + sku));

        BigDecimal oldPrice = product.getCurrentPrice();
        BigDecimal newPrice = product.getBasePrice().multiply(BigDecimal.valueOf(demandFactor));
        product.setCurrentPrice(newPrice);
        
        PriceChangedEvent event = PriceChangedEvent.builder()
                .productId(product.getId())
                .sku(product.getSku())
                .oldPrice(oldPrice)
                .newPrice(newPrice)
                .build();
        priceEventProducer.sendPriceChangedEvent(event);

        return product;
    }
}
