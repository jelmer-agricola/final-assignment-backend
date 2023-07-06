package nl.autogarage.finalassignmentbackendmain.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ErrorUtils {
    public static String errorToStringHandling(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : bindingResult.getFieldErrors()) {
            sb.append(fe.getField()).append(": ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
        }
        return sb.toString();
    }

}
