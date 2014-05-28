package com.zoowii.online_editor.orm

class OrderBy {
    def String sort
    def boolean asc

    OrderBy(String sort, boolean asc) {
        this.sort = sort
        this.asc = asc
    }

    def toOrderByString(Query query) {
        return "${sort} ${asc ? 'asc' : 'desc'}"
    }
}
