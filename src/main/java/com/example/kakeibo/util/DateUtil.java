package com.example.kakeibo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 日付操作Util
 * @author relation
 */
public class DateUtil {

    /**
     * 文字列をYearMonthに変換します。
     * @param yearMonth 対象の文字列
     * @return 変換結果
     */
    public static YearMonth parseYearMonth(String yearMonth) {
        try {
            return YearMonth.parse(yearMonth);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}