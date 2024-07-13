package com.example.shop_sv.modules.products;

import com.example.shop_sv.modules.products.dto.ProductRep;
import com.example.shop_sv.modules.products.dto.ProductReqUpdate;
import com.example.shop_sv.modules.products.dto.ProductResAdd;
import com.example.shop_sv.modules.users.model.dto.responne.UserRespone;
import com.example.shop_sv.modules.users.model.entity.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Object> getProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return new ResponseEntity<>(productService.getAllProducts(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductRep> getProductById(@PathVariable Integer productId) {
        System.out.println("đã vào");
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("search")
    public ResponseEntity<Page<ProductRep>> searchProduct(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam String search
    ) {
        return ResponseEntity.ok(productService.searchProductByName(page, pageSize, search));
    }


    @GetMapping("delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping( "update/{productId}")
    public ResponseEntity<Object> updateProduct(@PathVariable Integer productId, @Valid @RequestBody ProductReqUpdate productRq, @RequestAttribute("data") UserRespone user){
        try {
            if(!user.getPermission().contains("product.u")) {
                return new ResponseEntity<>("Khong co quyen", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("productRq: " + productRq.toString());
            return ResponseEntity.ok(productService.updateProduct(productId, productRq));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(@Valid @RequestBody ProductResAdd productRq,@RequestAttribute("data") UserRespone user) {
        try {
            if(!user.getPermission().contains("product.c")) {
                return new ResponseEntity<>("Khong co quyen", HttpStatus.UNAUTHORIZED);
            }
            ProductModel product = productService.addProduct(productRq);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((ProductModel) null, HttpStatus.BAD_REQUEST);
        }
    }
}
