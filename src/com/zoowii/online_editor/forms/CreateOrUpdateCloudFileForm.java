package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * Created by zoowii on 15/4/4.
 */
public class CreateOrUpdateCloudFileForm {
    @Null
    private Long id;
    @NotNull
    private int version;
    @NotNull
    private String content;
    @NotNull
    @Length(min = 3, max = 40)
    private String mimeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
