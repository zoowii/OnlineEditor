package onlineeditor

import com.zoowii.online_editor.utils.Common
import com.zoowii.online_editor.utils.UserCommon

class Account {

    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    Date lastUpdatedTime = new Date()
    String lastLoginIp = null
    Date lastLoginTime = new Date()
    String lastFailLoginIp = null
    Date lastFailLoginTime = null
    String userName
    String password
    String salt
    String aliasName
    String email
    Boolean deleted = false
    Boolean isGroupAccount = false
    String imageUrl = null
    String url = null
    String role = 'common'  // 'common', 'admin'
    String status = 'common'  // 'common', 'stopped', 'unregistered', etc.

    static constraints = {
        userName unique: true, blank: false
        email unique: true, email: true, blank: false
        lastLoginIp nullable: true
        lastFailLoginIp nullable: true
        lastFailLoginTime nullable: true
        imageUrl nullable: true
        url nullable: true
    }
    static mapping = {
        id generator: 'assigned'
    }

    static hasMany = [buckets: Bucket, tables: BigTable]

    def static initAccounts() {
        if (Account.count() > 0) {
            return
        }
        def user = new Account(userName: 'root', aliasName: 'root', email: 'root@localhost.com', role: 'admin')
        user.salt = Common.randomString(10)
        user.password = UserCommon.encryptPassword('root', user.salt)
        user.validate()
        if (user.hasErrors()) {
            println(user.getErrors())
        }
        user.save()
    }

    def static findByUserNameOrEmailByOne(String userNameOrEmail) {
        def user = Account.findByUserName(userNameOrEmail)
        if (user != null) {
            return user
        }
        return Account.findByEmail(userNameOrEmail)
    }

    def checkPassword(String password) {
        return this.password?.equals(UserCommon.encryptPassword(password, this.salt))
    }

}
