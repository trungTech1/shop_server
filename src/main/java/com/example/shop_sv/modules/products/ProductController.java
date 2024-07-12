package com.example.shop_sv.modules.products;

import com.example.shop_sv.modules.products.dto.ProductRep;
import com.example.shop_sv.modules.products.dto.ProductReqUpdate;
import com.example.shop_sv.modules.products.dto.ProductResAdd;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<Page<ProductRep>> getProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, pageSize));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductRep> getProductById(@PathVariable Integer productId) {
        System.out.println("đã vào");
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/product/search")
    public ResponseEntity<Page<ProductRep>> searchProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String search
    ) {
        return ResponseEntity.ok(productService.searchProductByName(page, pageSize, search));
    }


    @GetMapping("/product/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping( "/product/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductReqUpdate productRq) {
        try {
            System.out.println("dãong");
            return ResponseEntity.ok(productService.updateProduct(productId, productRq));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/product/add")
    public ResponseEntity<ProductModel> addProduct(@Valid @RequestBody ProductResAdd productRq) {
        try {
            return new ResponseEntity<ProductModel>(productService.addProduct(productRq), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ProductModel>((ProductModel) null, HttpStatus.BAD_REQUEST);
        }
    }
}
