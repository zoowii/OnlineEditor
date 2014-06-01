package com.zoowii.online_editor.orm

import com.zoowii.online_editor.utils.Common

/**
 * Query wrapper for ORM based on HQL
 * TODO: join, union, group, having, etc.
 * TODO: remember the class
 */
class Query {
    String tableName = null  // TODO: select from multi-tables
    Class cls = null
    def orderBys = []
    Expr condition = Expr.dummy()
//    def bindings = []
    def _tableSymbol = null
    def _limit = -1
    def _offset = -1

    def getTableSymbol() {
        if (_tableSymbol == null) {
            _tableSymbol = Common.randomString(5)
        }
        return _tableSymbol
    }

    /**
     * @param tableName maybe `User` or `User user`(then you can use user.name='abc' in expr)
     */
    Query(String tableName) {
        this.tableName = tableName
    }

    Query(Class cls) {
        this.cls = cls
        this.tableName = cls.getSimpleName()
    }

    def limit(int limit) {
        _limit = limit
        return this
    }

    def offset(int offset) {
        this._offset = offset
        return this
    }

    def where(Expr expr) {
        this.condition = expr
        return this
    }

    def clone() {
        def query = new Query(tableName)
        query._limit = this._limit
        query._offset = this._offset
        query._tableSymbol = this._tableSymbol
        query.condition = this.condition
        query.orderBys = this.orderBys
        return query
    }

    def eq(String name, Object value) {
        this.condition = this.condition.eq(name, value)
        return this
    }

    def ne(String name, Object value) {
        this.condition = this.condition.ne(name, value)
        return this
    }

    def lt(String name, Object value) {
        this.condition = this.condition.lt(name, value)
        return this
    }

    // TODO: add eq/ne/lt/etc.

    def orderBy(String sort, boolean asc = true) {
        this.orderBys.add(new OrderBy(sort, asc))
        return this
    }

    def asc(String sort) {
        return orderBy(sort, true)
    }

    def desc(String sort) {
        return orderBy(sort, false)
    }

    def getOrderByString() {
        def orderByStrs = this.orderBys.collect { orderBy ->
            return orderBy.toOrderByString(this)
        }
        return orderByStrs.join(',')
    }

    def toQuery() {
        def queryStr = "from ${tableName} "
        def exprQuery = this.condition?.toQueryString(this)
        if (exprQuery) {
            queryStr += " where ${exprQuery['query']}"
        }
        if (this.orderBys.size() > 0) {
            queryStr += " order by ${this.getOrderByString()}"
        }
        def bindings = []
        if (exprQuery) {
            bindings = exprQuery['bindings']
        }
        def extras = [dummy: 'dummy']
        if (this._limit >= 0) {
            extras['max'] = this._limit
        }
        if (this._offset >= 0) {
            extras['offset'] = this._offset
        }
        return [query: queryStr, bindings: bindings, extras: extras]
    }

    def count() {
        if (this.cls == null) {
            throw new Exception("you need pass a model class")
        }
        return count(this.cls)
    }

    def count(Class model) {
//        def query = this.toQuery()
//        return model.count(query)
        return 0 // TODO
    }

    def all() {
        if (this.cls == null) {
            throw new Exception("you need pass a model class")
        }
        return all(this.cls)
    }

    def all(Class model) {
        def query = this.toQuery()
        return model.findAll(query['query'], query['bindings'], query['extras'])
    }

    def first() {
        if (this.cls == null) {
            throw new Exception("you need pass a model class")
        }
        return first(this.cls)
    }

    def first(Class model) {
        def query = this.toQuery()
        return model.find(query['query'], query['bindings'], query['extras'])
    }

}
