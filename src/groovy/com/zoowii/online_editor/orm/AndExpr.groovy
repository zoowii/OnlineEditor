package com.zoowii.online_editor.orm

class AndExpr extends Expr {
    @Override
    def toQueryString(Query query) {
        Expr left = (Expr) items[0]
        Expr right = (Expr) items[1]
        def leftQuery = left.toQueryString(query)
        def rightQuery = right.toQueryString(query)
        def queryStr = "(${leftQuery['query']} ${op} ${rightQuery['query']})"
        def bindings = []
        bindings.addAll(leftQuery['bindings'])
        bindings.addAll(rightQuery['bindings'])
        return [query: queryStr, bindings: bindings]
    }
}
