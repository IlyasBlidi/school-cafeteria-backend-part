package com.frew.crew.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PostMapping
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        Category newCategory = Category.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();

        Category savedCategory = categoryService.saveCategory(newCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
}
