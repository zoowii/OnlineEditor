package onlineeditor

class EntityColumn {
    static final STRING_TYPE = 'string'
    static final NUMBER_TYPE = 'number'
    static final BOOLEAN_TYPE = 'boolean'
    static final DATE_TYPE = 'date'
    static final BINARY_TYPE = 'binary'  // not recommended and not supported now
    def static columnTypes = [
            ['string', 'String'],
            ['number', 'Number'],
            ['boolean', 'Boolean'],
            ['date', 'Date'],
            ['binary', 'Binary']
    ]
    String id = UUID.randomUUID().toString()
    Integer version = 1
    Date createdTime = new Date()
    String name
    String value = null
    String type = STRING_TYPE  // string, number, boolean, date, binary
    Boolean deleted = false
    static constraints = {
        value nullable: true
    }
    static mapping = {
        id generator: 'assigned'
        value type: 'text'
    }
    static belongsTo = [entity: Entity]
}
