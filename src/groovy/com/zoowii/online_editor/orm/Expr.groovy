package com.zoowii.online_editor.orm

/**
 * Expression for query
 */
class Expr {
    static EQ = '='
    static NE = '!='
    static LT = '<'
    static LE = '<='
    static GT = '>'
    static GE = '>='
    static OR = 'or'
    static AND = 'and'
    String op
    def items = []

    static class EmptyExpr extends Expr {
        @Override
        def toQueryString(Query query) {
            return [query: '1=1', bindings: []]
        }
    }

    def static Expr dummy() {
        return new EmptyExpr()
    }

    def static createEQ(String name, Object value) {
        return new Expr(op: EQ, items: [name, value])
    }

    def eq(String name, Object value) {
        return createAND(this, createEQ(name, value))
    }

    def static createNE(String name, Object value) {
        return new Expr(op: NE, items: [name, value])
    }

    def ne(String name, Object value) {
        return createAND(this, createNE(name, value))
    }

    def static createLT(String name, Object value) {
        return new Expr(op: LT, items: [name, value])
    }

    def lt(String name, Object value) {
        return createAND(this, createLT(name, value))
    }

    def static createLE(String name, Object value) {
        return new Expr(op: LE, items: [name, value])
    }

    def le(String name, Object value) {
        return createAND(this, createLE(name, value))
    }

    def static createGT(String name, Object value) {
        return new Expr(op: GT, items: [name, value])
    }

    def gt(String name, Object value) {
        return createAND(this, createGT(name, value))
    }

    def static createGE(String name, Object value) {
        return new Expr(op: GE, items: [name, value])
    }

    def ge(String name, Object value) {
        return createAND(this, createGE(name, value))
    }

    def static createOR(Expr left, Expr right) {
        return new OrExpr(op: OR, items: [left, right])
    }

    def or(Expr other) {
        return createOR(this, other)
    }

    def static createAND(Expr left, Expr right) {
        return new AndExpr(op: AND, items: [left, right])
    }

    def and(Expr other) {
        return createAND(this, other)
    }

    /**
     * parse to Query string and bindings
     */
    def toQueryString(Query query) {
        def queryStr = "${items[0]} ${op} ?"
        def bindings = [items[1]]
        return [query: queryStr, bindings: bindings]
    }

}
