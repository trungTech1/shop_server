package com.example.shop_sv.modules.categories;

import com.example.shop_sv.modules.categories.dto.resquest.CategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private  CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryModel>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try {
            Page<CategoryModel> result = categoryService.findAll(page, size);
            System.out.println("da vao cate" + result.toString());
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public CategoryModel getCategoryById(@PathVariable Integer id){
        return categoryService.getCategoryById(id);
    }
    @PutMapping("/{id}")
    public CategoryModel update(@PathVariable Integer id, @RequestBody CategoryRequest data){
        return categoryService.update(id, data);
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
