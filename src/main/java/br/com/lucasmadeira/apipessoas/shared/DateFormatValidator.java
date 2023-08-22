package br.com.lucasmadeira.apipessoas.shared;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;  // Permite campos vazios
        }

        try {
            LocalDate.parse(value, DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

