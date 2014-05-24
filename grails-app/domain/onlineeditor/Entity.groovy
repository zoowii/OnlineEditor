package onlineeditor

/**
 * DataStore Entity
 * if a CloudFile related to an Entity, then you can store data such as category/tags/comments of a blob article in it
 */
class Entity {

    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    Boolean deleted = false

    static constraints = {
    }

    static mapping = {
        id generator: 'assigned'
    }
    static hasMany = [columns: EntityColumn]
    static belongsTo = [table: BigTable]
}
