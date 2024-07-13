package com.example.shop_sv.modules.products;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Page<ProductModel> findByStatusTrue(Pageable pageable);
    // phương thức tìm kiếm toàn bộ product theo chuỗi tương đối
    @Query("SELECT p FROM ProductModel p WHERE p.name LIKE %:name% AND p.status = true")
    Page<ProductModel> findByProductNameContainingAndStatusTrue(String name, Pageable pageable);
}
