package com.example.kakeibo.controller;

import com.example.kakeibo.dto.CategoryDto;
import com.example.kakeibo.exception.CategoryDeletionException;
import com.example.kakeibo.request.CategoryRegisterRequest;
import com.example.kakeibo.service.CategoryService;
import com.example.kakeibo.util.ValidationErrorUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * カテゴリーの一覧を取得する
     * @return カテゴリーリスト
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        try {
            List<CategoryDto> categories = categoryService.getAllCategory();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * カテゴリーを登録する
     * @param request リクエストボディ：カテゴリー登録情報
     * @return 登録結果
     */
    @PostMapping
    public ResponseEntity<String> registerCategory(@Valid @RequestBody CategoryRegisterRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            return ResponseEntity.badRequest().body("【エラー】\n" + errorMessage);
        }

        try {
            categoryService.registerCategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("カテゴリーが登録されました");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * カテゴリーを削除する
     * @param categoryId カテゴリーID
     * @return 削除結果
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok("カテゴリーが削除されました");
        } catch (CategoryDeletionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

}
