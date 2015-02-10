package com.zoowii.online_editor.models;

import com.zoowii.jpa_utils.orm.Model;
import com.zoowii.jpa_utils.query.Finder;
import com.zoowii.online_editor.enums.UserRoles;
import com.zoowii.online_editor.enums.UserStatuses;
import com.zoowii.online_editor.utils.BCrypt;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import zuice.utils.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zoowii on 15/2/10.
 */
@Entity
@Table(name = "account")
public class AccountEntity extends Model {
    public static final Finder<Long, AccountEntity> find = new Finder<Long, AccountEntity>(Long.class, AccountEntity.class);
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer version = 1;
    private String aliasName;
    private Date createdTime = new Date();
    private Boolean deleted = false;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String imageUrl = null;
    private Boolean isGroupAccount = false;
    @Column(nullable = true)
    private String lastFailLoginIp = null;
    @Column(nullable = true)
    private Date lastFailLoginTime = null;
    @Column(nullable = true)
    private String lastLoginIp;
    @Column(nullable = true)
    private Date lastLoginTime = null;
    private Date lastUpdatedTime = new Date();
    private String password;
    private String role = UserRoles.COMMON;
    private String status = UserStatuses.COMMON;
    @Column(nullable = true)
    private String url;
    @Column(unique = true, nullable = false)
    private String userName;

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

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsGroupAccount() {
        return isGroupAccount;
    }

    public void setIsGroupAccount(Boolean isGroupAccount) {
        this.isGroupAccount = isGroupAccount;
    }

    public String getLastFailLoginIp() {
        return lastFailLoginIp;
    }

    public void setLastFailLoginIp(String lastFailLoginIp) {
        this.lastFailLoginIp = lastFailLoginIp;
    }

    public Date getLastFailLoginTime() {
        return lastFailLoginTime;
    }

    public void setLastFailLoginTime(Date lastFailLoginTime) {
        this.lastFailLoginTime = lastFailLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (deleted != that.deleted) return false;
        if (isGroupAccount != that.isGroupAccount) return false;
        if (version.equals(that.version)) return false;
        if (aliasName != null ? !aliasName.equals(that.aliasName) : that.aliasName != null) return false;
        if (createdTime != null ? !createdTime.equals(that.createdTime) : that.createdTime != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (imageUrl != null ? !imageUrl.equals(that.imageUrl) : that.imageUrl != null) return false;
        if (lastFailLoginIp != null ? !lastFailLoginIp.equals(that.lastFailLoginIp) : that.lastFailLoginIp != null)
            return false;
        if (lastFailLoginTime != null ? !lastFailLoginTime.equals(that.lastFailLoginTime) : that.lastFailLoginTime != null)
            return false;
        if (lastLoginIp != null ? !lastLoginIp.equals(that.lastLoginIp) : that.lastLoginIp != null) return false;
        if (lastLoginTime != null ? !lastLoginTime.equals(that.lastLoginTime) : that.lastLoginTime != null)
            return false;
        if (lastUpdatedTime != null ? !lastUpdatedTime.equals(that.lastUpdatedTime) : that.lastUpdatedTime != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + version;
        result = 31 * result + (aliasName != null ? aliasName.hashCode() : 0);
        result = 31 * result + (createdTime != null ? createdTime.hashCode() : 0);
        result = 31 * result + (deleted ? 1 : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (isGroupAccount ? 1 : 0);
        result = 31 * result + (lastFailLoginIp != null ? lastFailLoginIp.hashCode() : 0);
        result = 31 * result + (lastFailLoginTime != null ? lastFailLoginTime.hashCode() : 0);
        result = 31 * result + (lastLoginIp != null ? lastLoginIp.hashCode() : 0);
        result = 31 * result + (lastLoginTime != null ? lastLoginTime.hashCode() : 0);
        result = 31 * result + (lastUpdatedTime != null ? lastUpdatedTime.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    public static AccountEntity findByUserNameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail == null) {
            return null;
        }
        AccountEntity accountEntity = AccountEntity.find.where().eq("userName", usernameOrEmail).first();
        if (accountEntity != null) {
            return accountEntity;
        }
        return AccountEntity.find.where().eq("email", usernameOrEmail).first();
    }

    public boolean checkPassword(String password) {
        if (StringUtils.isEmpty(this.getPassword()) || StringUtils.isEmpty(password)) {
            return false;
        }
        return BCrypt.checkpw(password, this.getPassword());
    }

    @Transient
    public String getLastLoginTimeStr() {
        if (lastFailLoginTime == null) {
            return null;
        }
        return DateFormatUtils.format(lastLoginTime, "yyyy-MM-dd");
    }
}
