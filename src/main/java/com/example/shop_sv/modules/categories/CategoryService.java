package com.example.shop_sv.modules.categories;

import com.example.shop_sv.modules.categories.dto.respone.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<CategoryModel> findAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        try{
            return categoryRepository.findByStatusTrue(pageable);
        }catch (Exception e){
            return null;
        }
    }

    public CategoryModel save(CategoryRequest data){
        try{
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setName(data.getName());
            categoryModel.setIconUrl(data.getIconUrl());
            return  categoryRepository.save(categoryModel);
        }catch (Exception e){
            return null;
        }
    }

    public void delete(Integer id) {
        try {
            categoryRepository.deleteById(Long.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
