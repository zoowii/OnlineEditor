package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zoowii on 15/2/10.
 */
@Entity
@Table(name = "bucket")
public class BucketEntity extends Model {
    public static final Finder<Long, BucketEntity> find = new Finder<Long, BucketEntity>(Long.class, BucketEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer version = 1;
    private Date createdTime = new Date();
    @Column(unique = true)
    private String name;
    @ManyToOne
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountEntity getOwner() {
        return owner;
    }

    public void setOwner(AccountEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BucketEntity that = (BucketEntity) o;

        if (version.equals(that.version)) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
