package com.example.kakeibo.controller;

import com.example.kakeibo.dto.CategoryCommandDto;
import com.example.kakeibo.dto.CategoryDto;
import com.example.kakeibo.exception.CategoryDeletionException;
import com.example.kakeibo.request.CategoryRegisterRequest;
import com.example.kakeibo.response.ApiResponse;
import com.example.kakeibo.response.CategoryListResponse;
import com.example.kakeibo.response.errorDto.CategoryErrorDto;
import com.example.kakeibo.response.successDto.CategorySuccessDto;
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
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<ApiResponse> getAll() {
        try {
            List<CategoryDto> categories = categoryService.getAll();
            return ResponseEntity.ok(new CategoryListResponse(categories));
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
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CategoryRegisterRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            return ResponseEntity.badRequest().body(new CategoryErrorDto("【エラー】" + errorMessage));
        }

        try {
            CategoryCommandDto commandDto = CategoryCommandDto.from(request);
            categoryService.register(commandDto);
            return ResponseEntity.ok(new CategorySuccessDto("カテゴリーが登録されました"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * カテゴリーを削除する
     * @param categoryId カテゴリーID
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer categoryId) {
        try {
            categoryService.delete(categoryId);
            return ResponseEntity.noContent().build();
        } catch (CategoryDeletionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "サーバーエラーが発生しました");
        }
    }

}
