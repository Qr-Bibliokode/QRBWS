package qrbws

import grails.rest.Resource

@Resource(uri='/author')
class Author {

    String name
    String notes

    static constraints = {
        notes nullable: true
    }
}
