package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zoowii on 15/2/10.
 */
@Entity
@Table(name = "file_tag_mapping")
public class FileTagMappingEntity extends Model {
    public static final Finder<Long, FileTagMappingEntity> find = new Finder<Long, FileTagMappingEntity>(Long.class, FileTagMappingEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date createdTime = new Date();
    @ManyToOne
    private FileTagEntity tag;
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

    public FileTagEntity getTag() {
        return tag;
    }

    public void setTag(FileTagEntity tag) {
        this.tag = tag;
    }

    public CloudFileEntity getFile() {
        return file;
    }

    public void setFile(CloudFileEntity file) {
        this.file = file;
    }
}
