package com.zoowii.online_editor.forms;


/**
 * Created by zoowii on 15/1/25.
 */
public class SimpleResponse {
    public static final int SUCCESS = 200;
    public static final int UN_AUTHORIZED = 401;
    public static final int OTHER_ERROR = -1;
    private int code = SUCCESS;
    private boolean success = true;
    private Object data;
    private String errorMessage = null;

    public SimpleResponse() {
    }

    public SimpleResponse(int code, boolean success, Object data, String errorMessage) {
        this.code = code;
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static SimpleResponse makeSuccess(Object data) {
        return new SimpleResponse(SUCCESS, true, data, null);
    }

    public static SimpleResponse makeError(int code, String errorMessage) {
        return new SimpleResponse(code, false, null, errorMessage);
    }

    public static SimpleResponse makeCommonError(String errorMessage) {
        return makeError(OTHER_ERROR, errorMessage);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
