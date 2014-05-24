package onlineeditor

class CloudFile {

    String id = UUID.randomUUID().toString()
    Integer fileVersion = 1
    Integer version = 1  // record version
    Date createdTime = new Date()
    String name
    String description = null
    Boolean isPrivate = true
    Date lastUpdatedTime = new Date()
    String mimeType = 'text/plain; charset=UTF-8'
    String encoding = 'UTF-8'
    String content = ''
    Integer visitCount = 0
    Boolean deleted = false

    static constraints = {
        description nullable: true, blank: true
        content nullable: true, blank: true
    }
    static mapping = {
        id generator: 'assigned'
        content type: 'text'
    }
    static belongsTo = [author: Account, bucket: Bucket]

    def addChangeLog() {
        def fileLog = new FileLog(file: this, creator: this.author, content: this.content,
                mimeType: this.mimeType, name: this.name,
                description: this.description, encoding: this.encoding,
                isPrivate: this.isPrivate, fileVersion: this.fileVersion)
        fileLog.save()
    }
}
