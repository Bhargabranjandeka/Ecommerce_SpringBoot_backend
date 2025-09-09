package com.EcomMic.App.controller;

import com.EcomMic.App.dto.ProductRequest;
import com.EcomMic.App.dto.ProductResponse;
import com.EcomMic.App.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> search(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest productRequest) {
        return new ResponseEntity<ProductResponse>(productService.save(productRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        return productService.update(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
