package com.EcomMic.App.service;

import com.EcomMic.App.dto.ProductRequest;
import com.EcomMic.App.dto.ProductResponse;
import com.EcomMic.App.model.Product;
import com.EcomMic.App.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse save(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product savedProduct  = productRepository.save(product);
        return mapProductToResponse(savedProduct);
    }

    private ProductResponse mapProductToResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setQuantity(savedProduct.getQuantity());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        return productResponse;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setQuantity(productRequest.getQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public Optional<ProductResponse> update(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(exisitingUser->{
                  updateProductFromRequest(exisitingUser,productRequest);
                  Product savedProduct = productRepository.save(exisitingUser);
                  return mapProductToResponse(savedProduct);
                });


    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapProductToResponse).collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapProductToResponse).collect(Collectors.toList());
    }
}
