package onlineeditor

import grails.rest.RestfulController

class EntityController extends RestfulController {

    static responseFormats = ['json', 'xml']

    EntityController() {
        super(Entity)
    }
}
