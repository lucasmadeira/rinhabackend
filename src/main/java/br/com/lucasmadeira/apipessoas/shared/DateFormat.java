package br.com.lucasmadeira.apipessoas.shared;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateFormatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateFormat {
    String message() default "Data deve estar no formato AAAA/MM/DD";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

