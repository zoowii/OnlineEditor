package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zoowii on 15/4/4.
 */
@Entity
@Table(name = "blog_out_link")
public class BlogOutLinkEntity extends Model {
    public static final Finder<Long, BlogOutLinkEntity> find = new Finder<Long, BlogOutLinkEntity>(Long.class, BlogOutLinkEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String name;
    private String url;
    private Date createdTime = new Date();
    @ManyToOne
    private AccountEntity owner;
    private Boolean deleted = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
