package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.util.StringUtil;
import com.zoowii.online_editor.finders.CloudFileFinder;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zoowii on 15/2/10.
 */
@Entity
@Table(name = "cloud_file")
@Cacheable(false)
public class CloudFileEntity extends Model {
    public static final CloudFileFinder find = new CloudFileFinder(Long.class, CloudFileEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer fileVersion = 1;
    private Integer version = 1;
    private Date createdTime = new Date();
    private Date lastUpdatedTime = new Date();
    private Date date = new Date();
    private String name;
    @Column(nullable = true)
    private String description;
    private Boolean isPrivate = true;
    private String mimeType = "text/plain; charset=UTF-8";
    private String encoding = "UTF-8";
    private String tagsString = "";
    @Column(nullable = false)
    @Lob
    private String content = "";
    private Long visitCount = 0L;
    private Boolean deleted = false;
    @ManyToOne
    private AccountEntity author;
    @ManyToOne
    private BucketEntity bucket;
    @OneToMany(mappedBy = "file")
    private List<FileTagMappingEntity> fileTagMappings = new ArrayList<FileTagMappingEntity>();
    @OneToMany(mappedBy = "file")
    private List<FileAliasEntity> fileAliases = new ArrayList<FileAliasEntity>();

    public AccountEntity getAuthor() {
        return author;
    }

    public void setAuthor(AccountEntity author) {
        this.author = author;
    }

    public BucketEntity getBucket() {
        return bucket;
    }

    public void setBucket(BucketEntity bucket) {
        this.bucket = bucket;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFileVersion() {
        return fileVersion;
    }

    public void setFileVersion(Integer fileVersion) {
        this.fileVersion = fileVersion;
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

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<FileTagMappingEntity> getFileTagMappings() {
        return fileTagMappings;
    }

    public void setFileTagMappings(List<FileTagMappingEntity> fileTagMappings) {
        this.fileTagMappings = fileTagMappings;
    }

    public List<FileAliasEntity> getFileAliases() {
        return fileAliases;
    }

    public void setFileAliases(List<FileAliasEntity> fileAliases) {
        this.fileAliases = fileAliases;
    }

    public Date getDate() {
        return date == null ? new Date() : date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Transient
    public List<String> getTags() {
        return FileTagMappingEntity.getSession().findListByQuery(String.class,
                String.format("select distinct ft.tag.name from FileTagMappingEntity ft where ft.file.id=%d", this.getId()));
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    @Transient
    public String getDateString() {
        return DateFormatUtils.format(getDate(), "yyyy-MM-dd");
    }
}
