package com.example.kakeibo.controller;

import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.exception.ExpenseDeletionException;
import com.example.kakeibo.exception.InvalidYearMonthException;
import com.example.kakeibo.response.ExpenseResponse;
import com.example.kakeibo.request.ExpenseEditRequest;
import com.example.kakeibo.request.ExpenseRegisterRequest;
import com.example.kakeibo.service.ExpenseService;
import com.example.kakeibo.util.DateUtil;
import com.example.kakeibo.util.ValidationErrorUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * 月毎の支出を取得する
     * @param yearMonth リクエストパラメータ：年月
     * @return 支出リスト
     */
    @GetMapping
    public ResponseEntity<Object> getExpensesByMonth(@RequestParam String yearMonth) {
        if (StringUtils.isEmpty(yearMonth)) {
            return ResponseEntity.badRequest().body("対象月を入力してください");
        }

        try {
            YearMonth targetMonth = DateUtil.parseYearMonth(yearMonth);
            List<ExpenseDto> expenseListDto = expenseService.findExpensesByMonth(targetMonth);
            ExpenseResponse response = expenseService.createExpenseResponse(expenseListDto);
            return ResponseEntity.ok(response);
        } catch (InvalidYearMonthException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を登録する
     * @param request リクエストボディ：支出登録情報
     * @return 登録結果
     */
    @PostMapping
    public ResponseEntity<String> register(@Valid @RequestBody ExpenseRegisterRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            return ResponseEntity.badRequest().body("【エラー】\n" + errorMessage);
        }

        try {
            expenseService.register(request);
            return ResponseEntity.ok("支出が登録されました");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を編集する
     * @param expenseId 支出ID
     * @param request リクエストボディ：支出編集情報
     */
    @PutMapping("/{expenseId}")
    public void edit(@PathVariable Integer expenseId, @Valid @RequestBody ExpenseEditRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "【エラー】\n" + errorMessage);
        }

        try {
            expenseService.edit(expenseId, request);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を削除する
     * @param expenseId 支出ID
     */
    @DeleteMapping("/{expenseId}")
    public void delete(@PathVariable Integer expenseId) {
        try {
            expenseService.delete(expenseId);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ExpenseDeletionException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "サーバーエラーが発生しました");
        }
    }

}