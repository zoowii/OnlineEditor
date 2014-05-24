package onlineeditor

class FileLog {
    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    Date lastUpdatedTime = new Date()
    String content = null  // new file content
    String name = null
    String description
    Boolean isPrivate
    Integer fileVersion
    String mimeType = null // mime type
    String encoding = null // encoding
    Account creator

    static constraints = {
    }
    static mapping = {
        id generator: 'assigned'
    }
    static belongsTo = [file: CloudFile]
}
