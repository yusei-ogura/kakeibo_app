package com.example.kakeibo.util;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationErrorUtil {
    public static String formatErrorMessages(BindingResult result) {
        if (result.hasErrors()) {
            return result.getAllErrors().stream()
                    .map(error -> "・" + error.getDefaultMessage())
                    .distinct()
                    .collect(Collectors.joining("\n"));
        }
        return "";
    }

}
