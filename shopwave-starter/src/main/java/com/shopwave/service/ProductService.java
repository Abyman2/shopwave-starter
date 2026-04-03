package com.shopwave.service;

import com.shopwave.model.Product;
import com.shopwave.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductMapper.toDTO(product);
    }

    public ProductDTO createProduct(Product product) {
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        if (maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());
        }
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList());
    }

    public ProductDTO updateStock(Long id, int delta) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int newStock = product.getStock() + delta;
        if (newStock < 0) throw new IllegalArgumentException("Stock cannot be negative");

        product.setStock(newStock);
        return ProductMapper.toDTO(product);
    }
}