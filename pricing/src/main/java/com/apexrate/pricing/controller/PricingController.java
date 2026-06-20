package com.apexrate.pricing.controller;

import com.apexrate.pricing.entity.Product;
import com.apexrate.pricing.service.PricingService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pricing")
@RequiredArgsConstructor
public class PricingController {
    private final PricingService pricingService;

    @PostMapping("/trigger")
    public ResponseEntity<Product> triggerDynamicPricing(@RequestParam String sku, @RequestParam double demandFactor) {
        Product updatedProduct = pricingService.updatePriceDynamically(sku, demandFactor);
        return ResponseEntity.ok(updatedProduct);
    }
}
