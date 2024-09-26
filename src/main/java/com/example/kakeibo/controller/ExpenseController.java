package com.example.kakeibo.controller;

import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.exception.ExpenseDeletionException;
import com.example.kakeibo.form.ExpenseForm;
import com.example.kakeibo.request.ExpenseEditRequest;
import com.example.kakeibo.request.ExpenseRegisterRequest;
import com.example.kakeibo.service.ExpenseService;
import com.example.kakeibo.util.DateUtil;
import com.example.kakeibo.util.ValidationErrorUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/{yearMonth}")
    public ResponseEntity<Object> getExpensesByMonth(@PathVariable String yearMonth) {
        if (yearMonth == null || yearMonth.isEmpty()) {
            return ResponseEntity.badRequest().body("対象月を入力してください");
        }

        YearMonth targetMonth = DateUtil.parseYearMonth(yearMonth);
        if (targetMonth == null) {
            return ResponseEntity.badRequest().body("無効な年月のフォーマットです");
        }

        try {
            List<ExpenseDto> expenseListDto = expenseService.findExpenseListByMonth(targetMonth);
            ExpenseForm form = expenseService.createExpenseForm(expenseListDto);
            return ResponseEntity.ok(form);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を登録する
     * @param request リクエストボディ：支出登録情報
     * @return 登録結果
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerExpense(@Valid @RequestBody ExpenseRegisterRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            return ResponseEntity.badRequest().body("【エラー】\n" + errorMessage);
        }

        try {
            expenseService.registerExpense(request);
            return ResponseEntity.ok("支出が登録されました");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を編集する
     * @param expenseId 支出ID
     * @param request リクエストボディ：支出編集情報
     * @return 編集結果
     */
    @PostMapping("/edit/{expenseId}")
    public ResponseEntity<String> editExpense(@PathVariable Integer expenseId, @Valid @RequestBody ExpenseEditRequest request, BindingResult result) {
        String errorMessage = ValidationErrorUtil.formatErrorMessages(result);
        if (!errorMessage.isEmpty()) {
            return ResponseEntity.badRequest().body("【エラー】\n" + errorMessage);
        }

        try {
            expenseService.editExpense(expenseId, request);
            return ResponseEntity.ok("支出が編集されました");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

    /**
     * 支出を削除する
     * @param expenseId 支出ID
     * @return 削除結果
     */
    @DeleteMapping("/{expenseId}")
    public ResponseEntity<String> deleteExpense(@PathVariable Integer expenseId) {
        try {
            expenseService.deleteExpense(expenseId);
            return ResponseEntity.ok("支出が削除されました");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ExpenseDeletionException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("サーバーエラーが発生しました");
        }
    }

}