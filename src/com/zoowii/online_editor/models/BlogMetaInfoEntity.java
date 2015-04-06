package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zoowii on 15/4/6.
 */
@Entity
@Table(name = "blog_meta_info")
@Cacheable(false)
public class BlogMetaInfoEntity extends Model {
    public static final Finder<Long, BlogMetaInfoEntity> find = new Finder<Long, BlogMetaInfoEntity>(Long.class, BlogMetaInfoEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer version = 1;
    private Date createdTime = new Date();
    private Date lastUpdatedTime = new Date();
    private String siteTitle;
    private String blogHost;
    private String authorNickName;
    @OneToOne
    private AccountEntity owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    public String getBlogHost() {
        return blogHost;
    }

    public void setBlogHost(String blogHost) {
        this.blogHost = blogHost;
    }

    public String getAuthorNickName() {
        return authorNickName;
    }

    public void setAuthorNickName(String authorNickName) {
        this.authorNickName = authorNickName;
    }

    public static BlogMetaInfoEntity getBlogMetaInfoOfUser(AccountEntity user) {
        BlogMetaInfoEntity blogMetaInfoEntity = BlogMetaInfoEntity.find.where().eq("owner", user).findUnique();
        if(blogMetaInfoEntity == null) {
            blogMetaInfoEntity = new BlogMetaInfoEntity();
            if(user == null) {
                blogMetaInfoEntity.setAuthorNickName("");
                blogMetaInfoEntity.setBlogHost("http://localhost:8080");
                blogMetaInfoEntity.setSiteTitle("");
                return blogMetaInfoEntity;
            }
            blogMetaInfoEntity.setAuthorNickName(user.getUserName());
            blogMetaInfoEntity.setBlogHost("http://localhost:8080");
            blogMetaInfoEntity.setOwner(user);
            blogMetaInfoEntity.setSiteTitle("Blog Site Title");
            blogMetaInfoEntity.save();
        }
        return blogMetaInfoEntity;
    }
}
