package com.shopwave.controller;

import com.shopwave.service.ProductDTO;
import com.shopwave.service.ProductNotFoundException;
import com.shopwave.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products?page=0&size=10
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductDTO> products = productService.getAllProducts(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDTO created = productService.createProduct(request.toProduct());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/products/search?keyword=&maxPrice=
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        List<ProductDTO> results = productService.searchProducts(keyword, maxPrice);
        return ResponseEntity.ok(results);
    }

    // PATCH /api/products/{id}/stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody StockUpdateRequest request
    ) {
        ProductDTO updated = productService.updateStock(id, request.delta());
        return ResponseEntity.ok(updated);
    }

    // ----------------- Request DTOs -----------------
    public static class CreateProductRequest {
        @NotBlank
        public String name;
        public String description;
        @Min(0)
        public BigDecimal price;
        @Min(0)
        public Integer stock;
        public Long categoryId;

        public com.shopwave.model.Product toProduct() {
            com.shopwave.model.Product p = new com.shopwave.model.Product();
            p.setName(this.name);
            p.setDescription(this.description);
            p.setPrice(this.price);
            p.setStock(this.stock);
            // TODO: set category by id if needed
            return p;
        }
    }

    public static record StockUpdateRequest(int delta) {}
}