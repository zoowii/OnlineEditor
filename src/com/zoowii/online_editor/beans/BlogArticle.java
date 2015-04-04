package com.zoowii.online_editor.beans;

import com.zoowii.online_editor.models.AccountEntity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zoowii on 15/4/4.
 */
public class BlogArticle {
    private long id;
    private String html;
    private List<String> tags = new ArrayList<String>();
    private String title;
    private String summary;
    private Date createdTime;
    private Date date;
    private AccountEntity author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFormatedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return date != null ? dateFormat.format(date) : "";
    }

    public AccountEntity getAuthor() {
        return author;
    }

    public void setAuthor(AccountEntity author) {
        this.author = author;
    }
}
