package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by zoowii on 15/4/4.
 */
public class CreateCloudFileForm {
    @Length(min = 3, max = 100)
    @NotNull
    private String name;
    @Length(min = 0, max = 250)
    private String description;
    private String isPrivate;
    @NotNull
    private long bucketId;

    public boolean isPrivateBool() {
        return "on".equals(isPrivate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }
}
