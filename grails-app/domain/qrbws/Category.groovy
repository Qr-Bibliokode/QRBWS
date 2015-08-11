package qrbws

import grails.rest.Resource

@Resource(uri = '/api/category', formats=['json'])
class Category {

    String description

    static constraints = {
        description maxSize: 254
    }
}
