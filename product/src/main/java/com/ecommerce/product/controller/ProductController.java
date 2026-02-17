package com.ecommerce.product.controller;


import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        ProductResponse createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

    }

    @GetMapping("/fetch")
    public ResponseEntity<List<ProductResponse>> fetchAllProduct(){
        List<ProductResponse> AllProducts = productService.getAllProduct();
        return new ResponseEntity<>(AllProducts, HttpStatus.OK);
    }

    @GetMapping("/fetch/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id){
        Optional<ProductResponse> product = productService.getProductById(id);
        return  product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest){
        return productService.updateProduct(id, productRequest)
                .map(ResponseEntity::ok)
                .orElseGet( () -> ResponseEntity.notFound().build());

    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id){
        boolean deleted = productService.removeProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping ("/search")
    public ResponseEntity<List<ProductResponse>> findProduct(
            @RequestParam String keyword){

        return ResponseEntity.ok(productService.searchProduct(keyword));

    }


}
