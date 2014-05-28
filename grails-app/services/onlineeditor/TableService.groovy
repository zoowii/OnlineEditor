package onlineeditor

import com.zoowii.online_editor.orm.Expr
import com.zoowii.online_editor.orm.Query
import grails.transaction.Transactional

/**
 * wrapper for BigTable api
 */
@Transactional
class TableService {

    def _createQuery(BigTable table, Expr expr) {
        def query = new Query('EntityColumn')
        query = query.eq("entity.table", table)
        if (expr != null) {
            switch (expr.op) {
                case Expr.EQ:
                    query = query.eq('name', expr.items[0]).eq('value', expr.items[1])
                    break;
                case Expr.NE:
                    query = query.eq('name', expr.items[0]).ne('value', expr.items[1])
                    break;
                case Expr.LT:
                    query = query.eq('name', expr.items[0]).lt('value', expr.items[1])
                    break;
                case Expr.LE:
                    query = query.eq('name', expr.items[0]).le('value', expr.items[1])
                    break;
                case Expr.GT:
                    query = query.eq('name', expr.items[0]).gt('value', expr.items[1])
                    break;
                case Expr.GE:
                    query = query.eq('name', expr.items[0]).ge('value', expr.items[1])
                    break;
                default:
                    throw new UnsupportedOperationException('other expr op type is not supported now')
            }
        }
        return query
    }

    /**
     * tableName is the BigTable name
     * exprs are column-name -> value -> op(=,>,<,etc.) pairs, now only support one epxr
     * @param tableName
     * @param exprs
     */
    def findAll(BigTable table, Expr expr) {
        def query = _createQuery(table, expr)
        def entityCols = query.all(EntityColumn)
        def entities = []
        entityCols.each { col ->
            if (!(col.entity in entities)) {
                entities.add(col.entity)
            }
        }
        return entities
    }

    def find(BigTable table, Expr expr) {
        def query = _createQuery(table, expr)
        def entityCol = query.first(EntityColumn)
        return entityCol?.entity
    }

    def findById(BigTable table, String entityId) {
        def query = new Query('EntityColumn')
        query = query.eq('entity.id', entityId)
        return query.first(EntityColumn)
    }

    def getEntityColumnValue(Entity entity, String columnName) {
        return getEntityColumn(entity, columnName)?.value
    }

    def getEntityColumn(Entity entity, String columnName) {
        def query = new Query('EntityColumn')
        query = query.eq('name', columnName)
        return query.first(EntityColumn)
    }

    def insertColumn(Entity entity, String name, Object value) {
        def col = new EntityColumn(entity: entity, name: name, value: value)
        col.save()
        return col
    }
}
