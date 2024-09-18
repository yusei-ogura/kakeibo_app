package com.example.kakeibo.controller;

import com.example.kakeibo.dto.ExpenseDto;
import com.example.kakeibo.service.ExpenseService;
import com.example.kakeibo.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * 月毎の支出を取得する
     * @param yearMonth リクエストパラメータ：年月
     * @return 支出リスト
     */
    @GetMapping("/{yearMonth}")
    public ResponseEntity<?> getExpensesByMonth(@PathVariable(required = false) String yearMonth) {

        if (yearMonth == null || yearMonth.isEmpty()) {
            return ResponseEntity.badRequest().body("対象月を入力してください");
        }

        YearMonth targetMonth = DateUtil.parseYearMonth(yearMonth);
        if (targetMonth == null) {
            // 年月の解析に失敗した場合のエラーメッセージ
            return ResponseEntity.badRequest().body("無効なフォーマットです");
        }

        List<ExpenseDto> expenseListDto = expenseService.findExpenseListByMonth(targetMonth);
        return ResponseEntity.ok(expenseListDto);
    }
}


