package onlineeditor

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

    def static getOrCreate(Account user, String name) {
        def bucket = findByOwnerAndName(user, name)
        if (bucket != null) {
            return bucket
        }
        bucket = new Bucket(name: name, owner: user)
        bucket.save()
        return bucket
    }

}
