package com.zoowii.online_editor.utils;

import com.zoowii.formutils.BindingResult;
import com.zoowii.formutils.ValidateError;

import java.util.Iterator;

/**
 * Created by zoowii on 15/1/25.
 */
public class Common {
    public static String validatorErrorsToString(BindingResult bindingResult) {
        if (bindingResult == null) {
            return null;
        }
        Iterator<ValidateError> validateErrors = bindingResult.getErrors();
        StringBuilder builder = new StringBuilder();
        while (validateErrors.hasNext()) {
            ValidateError error = validateErrors.next();
            if (!builder.toString().isEmpty()) {
                builder.append(",");
            }
            builder.append(error.getErrorMessage());
        }
        return builder.toString();
    }
}
