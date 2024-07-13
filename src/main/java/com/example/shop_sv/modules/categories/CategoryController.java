package com.example.shop_sv.modules.categories;

import com.example.shop_sv.modules.categories.dto.request.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;

    @GetMapping
    public Page<CategoryModel> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return categoryService.findAll(page, size);
    }
    @PostMapping
    public CategoryModel save(@RequestBody CategoryRequest data){
        return categoryService.save(data);
    }
    @DeleteMapping( "/{id}")
    public void delete(@PathVariable Integer id){
        categoryService.delete(id);
    }
    @PutMapping( "/{id}")
    public CategoryModel update(@PathVariable Integer id, @RequestBody CategoryRequest data){
        return categoryService.update(id, data);
    }

}
