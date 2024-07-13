package com.example.shop_sv.modules.products;

import com.example.shop_sv.modules.categories.CategoryService;
import com.example.shop_sv.modules.imageProduct.ImageProductModel;
import com.example.shop_sv.modules.products.dto.ProductRep;
import com.example.shop_sv.modules.products.dto.ProductReqUpdate;
import com.example.shop_sv.modules.products.dto.ProductResAdd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    public Page<ProductRep> getAllProducts(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductModel> products = productRepository.findByStatusTrue(pageable);
        return products.map(this::convertToProductRep);
    }

    public ProductModel addProduct(ProductResAdd productRq) {
        ProductModel product = new ProductModel();
        product.setName(productRq.getProduct_name());
        product.setDescription(productRq.getDescription());
        product.setPrice(productRq.getUnitPrice());
        product.setStockQuantity(productRq.getStock_quantity());
        product.setCategory(categoryService.getCategoryById(productRq.getCategory_id()));
        product.setCreatedDate(new Date().toString());
        product.setStatus(true);
        System.out.println("images: " + productRq.getImages());
        List<ImageProductModel> images = new ArrayList<>();
        for (String imageUrl : productRq.getImages()) {
            ImageProductModel imageProduct = new ImageProductModel();
            imageProduct.setImageUrl(imageUrl);
            images.add(imageProduct);
        }
        product.setImages(images);
        productRepository.save(product);
        return product;
    }

    public void deleteProduct(Integer productId) {
        Optional<ProductModel> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            ProductModel product = productOpt.get();
            product.setStatus(false);
            productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public ProductRep getProductById(Integer productId) {
        Optional<ProductModel> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            return convertToProductRep(productOpt.get());
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public Page<ProductRep> searchProductByName(int page, int pageSize, String search) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<ProductModel> products = productRepository.findByProductNameContainingAndStatusTrue(search, pageable);
        return products.map(this::convertToProductRep);
    }
    public ProductRep convertToProductRep(ProductModel product) {
        ProductRep productRep = new ProductRep();
        productRep.setProduct_id(product.getId());
        productRep.setProduct_name(product.getName());
        productRep.setDescription(product.getDescription());
        productRep.setUnitPrice(product.getPrice());
        productRep.setStock_quantity(product.getStockQuantity());
        productRep.setCategory_id(product.getCategory().getId());
        productRep.setStatus(product.getStatus());
        productRep.setCreatedDate(product.getCreatedDate());
        productRep.setUpdatedDate(product.getUpdatedDate());
        productRep.setImageUrls(product.getImages().stream().map(ImageProductModel::getImageUrl).collect(Collectors.toList()));
        return productRep;
    }

    public String updateProduct(Integer productId, ProductReqUpdate productRq) {
        Optional<ProductModel> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            ProductModel product = productOpt.get();
            product.setName(productRq.getProduct_name());
            product.setDescription(productRq.getDescription());
            product.setPrice(productRq.getUnitPrice());
            product.setStockQuantity(productRq.getStock_quantity());
            product.setCategory(categoryService.getCategoryById(productRq.getCategory_id()));
            product.setUpdatedDate(new Date().toString());
            product.getImages().clear();
            System.out.println("images: " + productRq.getImageUrls());
            for (String imageUrl : productRq.getImageUrls()) {
                ImageProductModel imageProduct = new ImageProductModel();
                imageProduct.setImageUrl(imageUrl);
                product.getImages().add(imageProduct);
            }
            productRepository.save(product);
            return "Product updated successfully";
        } else {
            throw new RuntimeException("Product not found");
        }
    }
    private void updateProductImages(ProductModel product, List<String> newImageUrls) {
        // Tạo một set của các URL hình ảnh hiện tại
        Set<String> currentImageUrls = product.getImages().stream()
                .map(ImageProductModel::getImageUrl)
                .collect(Collectors.toSet());
        // Xóa những hình ảnh không còn trong danh sách mới
        product.getImages().removeIf(image -> !newImageUrls.contains(image.getImageUrl()));

        // Thêm những hình ảnh mới
        newImageUrls.forEach(url -> {
            if (!currentImageUrls.contains(url)) {
                ImageProductModel newImage = new ImageProductModel();
                newImage.setImageUrl(url);
                product.getImages().add(newImage);
            }
        });

        // Nếu không có hình ảnh nào, có thể thêm một hình ảnh mặc định hoặc để trống
        if (product.getImages().isEmpty()) {
            ImageProductModel defaultImage = new ImageProductModel();
            defaultImage.setImageUrl("default-image-url");
            product.getImages().add(defaultImage);
        }
    }
}
