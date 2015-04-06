package com.zoowii.online_editor.forms;

import com.zoowii.formutils.annotations.Length;
import com.zoowii.playmore.util.ListUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

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
    @NotNull
    @Length(min = 0, max = 250)
    private String tags = "";

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

    public String getTags() {
        return tags;
    }

    public Set<String> getTagsSet() {
        Set<String> result = new HashSet<String>();
        if(tags != null) {
            for(String tag : tags.split("\\s+")) {
                if(tag.length()>0) {
                    result.add(tag);
                }
            }
        }
        return result;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
