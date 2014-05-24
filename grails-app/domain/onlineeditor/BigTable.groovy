package onlineeditor

/**
 * DataStore BigTable
 * every user can has many tables with distinct names, but different users can have same name tables
 */
class BigTable {
    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    String name
    Boolean deleted = false
    Long totalCount = 0
    static constraints = {
    }
    static mapping = {
        id generator: 'assigned'
    }
    static belongsTo = [owner: Account]
    static hasMany = [entities: Entity]

    def addEntity(Entity entity) {
        this.entities.add(entity)
        entity.table = this
        this.version += 1
        this.totalCount += 1
    }
}
