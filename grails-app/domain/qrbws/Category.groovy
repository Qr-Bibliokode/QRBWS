package qrbws

import grails.rest.Resource

@Resource(uri='/category')
class Category {

    String description

    static constraints = {
        description maxSize: 254
    }
}
