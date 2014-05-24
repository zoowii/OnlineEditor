package onlineeditor

import com.zoowii.online_editor.utils.Paginator

/**
 * every user can has some buckets, with files stored in a bucket
 * TODO: every bucket can has its ACL permissions
 */
class Bucket {

    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    String name

    static constraints = {
        name unique: true
    }
    static mapping = {
        id generator: 'assigned'
    }
    static belongsTo = [owner: Account]
    static hasMany = [files: CloudFile]

}
