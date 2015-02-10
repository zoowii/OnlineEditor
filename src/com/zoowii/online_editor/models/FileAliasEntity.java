package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;

/**
 * 每个文件或者博客文章可以有若干个唯一的别名,用来迁移,以及生成方便记忆的url等
 * Created by zoowii on 15/2/10.
 */
@Entity
@Table(name = "file_alias")
public class FileAliasEntity extends Model {
    public static final Finder<Long, FileAliasEntity> find = new Finder<Long, FileAliasEntity>(Long.class, FileAliasEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdTime = new Date();
    private Boolean deleted = false;
    @Column(unique = true)
    private String name;
    @ManyToOne
    private CloudFileEntity file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Boolean getDeleted() {
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

    public CloudFileEntity getFile() {
        return file;
    }

    public void setFile(CloudFileEntity file) {
        this.file = file;
    }
}
