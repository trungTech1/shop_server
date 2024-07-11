package com.example.shop_sv.modules.categories;

import com.example.shop_sv.modules.categories.dto.respone.CategoryRequest;
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
        System.out.println("da vao"+data.getName());
        return categoryService.save(data);
    }
    @DeleteMapping( "/{id}")
    public void delete(@PathVariable Integer id){
        System.out.println("da vao"+id);
        categoryService.delete(id);
    }
}
