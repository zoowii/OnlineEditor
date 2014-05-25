package onlineeditor

import com.zoowii.online_editor.utils.Paginator
import grails.transaction.Transactional

@Transactional
class TableController extends BaseController {

    def beforeInterceptor = [action: this.&auth, except: []]

    def static allowedMethods = [deleteEntity: 'POST', createOrUpdateEntityColumn: 'POST']

    def list() {
        def user = currentUser()
        def paginator = new Paginator(request)
        def query = "from BigTable as tbl where owner=? order by createdTime desc"
        def tables = Bucket.findAll(query, [user], [max: paginator.pageSize, offset: paginator.getOffset()])
        paginator.setTotalCount(BigTable.countByOwner(user))
        [tables: tables, paginator: paginator]
    }

    def listEntities(String id) {
        def user = currentUser()
        def table = BigTable.findById(id)
        if (table == null || table.owner != user) {
            flash.error = "The table ${id} not exist or you aren't the owner of this table"
            redirect(action: 'list')
            return
        }
        def paginator = new Paginator(request)
        def query = "from Entity as e where e.table=? order by createdTime desc"
        def entities = Entity.findAll(query, [table], [max: paginator.pageSize, offset: paginator.getOffset()])
        paginator.setTotalCount(table.totalCount)
        [table: table, user: user, entities: entities, paginator: paginator]
    }

    def createEntityPage(String tableId) {
        def user = currentUser()
        def table = BigTable.findById(tableId)
        if (table == null || table.owner != user) {
            flash.error = "The table ${tableId} not exist or you aren't the owner of this table"
            redirect(action: 'listEntities', params: [id: tableId])
            return
        }
        def entity = new Entity(table: table)
        entity.validate()
        if (entity.hasErrors()) {
            flash.error = entity.getErrors().toString()
            redirect(action: 'listEntities', params: [id: tableId])
        }
        entity.save()
        table.addEntity(entity)
        table.save()
        redirect(action: 'listEntities', params: [id: tableId])
    }

    def editEntityPage(String tableId, String entityId) {
        def user = currentUser()
        def table = BigTable.findById(tableId)
        if (table == null || table.owner != user) {
            flash.error = "The table ${tableId} not exist or you aren't the owner of this table"
            redirect(action: 'listEntities', params: [id: tableId])
            return
        }
        def entity = Entity.findById(entityId)
        def isNew = entity == null
        if (!isNew && entity.table != table) {
            flash.error = "this entity is not in table ${table.name}"
            redirect(action: 'listEntities', params: [id: tableId])
            return
        }
        if (isNew) {
            entity = new Entity(table: table)
        }
        def columnTypes = EntityColumn.columnTypes
        def newColumn = new EntityColumn(entity: entity)
        render view: 'editEntityPage', model: [
                table: table, entity: entity, user: user, isNew: isNew, columnTypes: columnTypes, newColumn: newColumn
        ]
    }

    def deleteEntity(String id) {
        // TODO
    }

    def createOrUpdateEntityColumn() {
        def user = currentUser()
        String entityId = params.entityId
        String columnId = params.columnId
        def entity = Entity.findById(entityId)
        def column = EntityColumn.findById(columnId)
        String name = params.name
        if (!name || name.length() < 1) {
            flash.error = "column's name can't be empty"
            redirect(action: 'editEntityPage', params: [tableId: entity?.table?.id, entityId: entity?.id])
            return
        }
        String type = params.type
        String value = params.value
        if (entity?.table?.owner != user) {
            flash.error = "You aren't the owner of this entity"
            redirectToIndex()
            return
        }
        def columnWithThisName = EntityColumn.findByNameAndEntity(name, entity)
        if (column != null) {
            if (column?.entity != entity) {
                flash.error = 'this column is not in this entity'
                redirectToIndex()
                return
            }
            if (columnWithThisName != null && columnWithThisName != column) {
                flash.error = "column of this entity with name ${name} exists"
                redirectToIndex()
                return
            }
        } else {
            if (columnWithThisName != null) {
                flash.error = "column of this entity with name ${name} exists"
                redirectToIndex()
                return
            }
            column = new EntityColumn(entity: entity)
        }
        column.name = name
        column.value = value
        column.type = type
        column.validate()
        if (column.hasErrors()) {
            flash.error = column.getErrors()
            redirectToIndex()
            return
        }
        column.save()
        redirect(action: 'editEntityPage', params: [tableId: entity?.table?.id, entityId: entity?.id])
    }

    def createPage() {
        editPage(null)
    }

    def editPage(String id) {
        def table = BigTable.findById(id)
        def isNew = table == null
        if (isNew) {
            table = new BigTable()
        }
        render view: 'edit', model: [table: table, isNew: isNew]
    }

    def createOrUpdate(String id) {
        def user = currentUser()
        String name = params.name
        if (!name || name.length() < 1) {
            flash.error = "table name can't be empty"
            redirect(action: 'editPage', params: [id: id])
            return
        }
        def table = BigTable.findById(id)
        def isNew = table == null
        if (BigTable.findByNameAndOwner(name, user) != null) {
            flash.error = "table '${name}' exists!"
            redirect(action: 'editPage', params: [id: id])
            return
        }
        if (isNew) {
            table = new BigTable(owner: user, name: name)
        } else {
            if (table.owner != user) {
                flash.error = "You are not the owner of this table"
                redirect(action: 'editPage', params: [id: id])
                return
            }
            table.name = name
        }
        table.validate()
        if (table.hasErrors()) {
            flash.error = table.getErrors()
            redirect(action: 'editPage', params: [id: id])
            return
        }
        table.save()
        flash.success = "Created/Edit table '${name}' successfully"
        redirect(action: 'list')
    }

}
