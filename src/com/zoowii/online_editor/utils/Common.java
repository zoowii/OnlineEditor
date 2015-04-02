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
    public static int tryParseInt(Object obj, int defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Integer) {
            return (Integer) obj;
        }
        try {
            return Integer.valueOf(obj.toString());
        } catch(Exception e) {
            return defaultValue;
        }
    }
    public static long tryParseLong(Object obj, long defaultValue) {
        if(obj == null) {
            return defaultValue;
        }
        if(obj instanceof Long) {
            return (Long) obj;
        }
        try {
            return Long.valueOf(obj.toString());
        } catch(Exception e) {
            return defaultValue;
        }
    }
    public static int tryParseInt(Object obj) {
        return tryParseInt(obj, 0);
    }
    public static long tryParseLong(Object obj) {
        return tryParseLong(obj, 0L);
    }
}
