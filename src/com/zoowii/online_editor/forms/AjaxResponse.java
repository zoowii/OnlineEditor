package com.zoowii.online_editor.forms;

/**
 * Created by zoowii on 15/4/4.
 */
public class AjaxResponse {
    private int code = 0;
    private boolean success = true;
    private Object data;

    public AjaxResponse(int code, boolean success, Object data) {
        this.code = code;
        this.success = success;
        this.data = data;
    }

    public AjaxResponse(boolean success, Object data) {
        this.success = success;
        this.code = success ? 0 : 100;
        this.data = data;
    }

    public AjaxResponse(Object data) {
        this(true, data);
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
}
