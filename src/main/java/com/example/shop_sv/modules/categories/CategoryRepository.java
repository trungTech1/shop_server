package com.example.shop_sv.modules.categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository <CategoryModel, Integer>{
    Page<CategoryModel> findByStatusTrue(Pageable pageable);
}
